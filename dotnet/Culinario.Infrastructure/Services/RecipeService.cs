using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Culinario.Domain.Models;
using Culinario.Domain.Repositories;
using Culinario.Infrastructure.Persistence;
using Culinario.Infrastructure.Persistence.Entities;
using Microsoft.EntityFrameworkCore;

namespace Culinario.Infrastructure.Services;

public class RecipeService : IRecipeService
{
    private static readonly Mutex RecipeMutationMutex = new();
    private static readonly SemaphoreSlim RecipeMutationGate = new(1, 1);
    private readonly IRecipeRepository _recipeRepository;
    private readonly IShoppingListRepository _shoppingListRepository;
    private readonly CulinarioDbContext _context;

    public RecipeService(IRecipeRepository recipeRepository, IShoppingListRepository shoppingListRepository, CulinarioDbContext context)
    {
        _recipeRepository = recipeRepository;
        _shoppingListRepository = shoppingListRepository;
        _context = context;
    }

    public async Task<List<RecipeDTO>> GetAllAsync()
    {
        var recipes = await _recipeRepository.FindAllAsync();
        return recipes.Select(ToDTO).ToList();
    }

    public async Task<RecipeDTO> GetAsync(int id)
    {
        var recipe = await _recipeRepository.FindByIdAsync(id);
        if (recipe == null) throw new Exception($"Recipe not found with id: {id}");
        return ToDTO(recipe);
    }

    public async Task CreateAsync(NewRecipeDTO dto, string ownerUsername)
    {
        await RecipeMutationGate.WaitAsync();
        try
        {
            RecipeMutationMutex.WaitOne();
            try
            {
                int? shoppingListId = null;

                if (dto.ShoppingList != null && !string.IsNullOrWhiteSpace(dto.ShoppingList.Title))
                {
                    var shoppingList = new ShoppingList
                    {
                        Title = dto.ShoppingList.Title,
                        Description = dto.ShoppingList.Description,
                        Items = dto.ShoppingList.Items.Select(i => new ShoppingListItem { Name = i.Name, Amount = i.Amount }).ToList()
                    };
                    var savedList = await _shoppingListRepository.SaveAsync(shoppingList);
                    shoppingListId = savedList.Id;
                }

                var recipe = new Recipe
                {
                    Title = dto.Title,
                    Description = dto.Description,
                    Steps = dto.Steps.Select(s => new RecipeStep { Title = s.Title, Description = s.Description }).ToList(),
                    Owner = ownerUsername,
                    Category = dto.Category,
                    Created = DateTime.Now.ToString("yyyy-MM-dd"),
                    ShoppingListId = shoppingListId
                };

                await _recipeRepository.SaveAsync(recipe);

                if (dto.ShoppingList != null && dto.ShoppingList.Items.Any())
                {
                    foreach (var item in dto.ShoppingList.Items.Where(x => !string.IsNullOrWhiteSpace(x.Name)))
                    {
                        var normalized = item.Name.Trim();
                        var ingredient = await _context.Ingredients.FirstOrDefaultAsync(x => x.NormalizedName == normalized.ToLowerInvariant());

                        if (ingredient == null)
                        {
                            ingredient = new IngredientEntity
                            {
                                Name = normalized,
                                NormalizedName = normalized.ToLowerInvariant()
                            };
                            _context.Ingredients.Add(ingredient);
                            await _context.SaveChangesAsync();
                        }

                        var existingLink = await _context.RecipeIngredients.FirstOrDefaultAsync(x => x.RecipeId == recipe.Id && x.IngredientId == (ingredient.Id ?? 0));
                        if (existingLink == null)
                        {
                            _context.RecipeIngredients.Add(new RecipeIngredientEntity
                            {
                                RecipeId = recipe.Id,
                                IngredientId = ingredient.Id ?? 0,
                                Name = normalized,
                                Amount = item.Amount,
                                Unit = "pcs"
                            });
                        }
                    }

                    await _context.SaveChangesAsync();
                }

                RecipeRealtimeState.QueueSignal($"New recipe added: {recipe.Title} by {ownerUsername}");
            }
            finally
            {
                RecipeMutationMutex.ReleaseMutex();
            }
        }
        finally
        {
            RecipeMutationGate.Release();
        }
    }

    public async Task DeleteAsync(int id)
    {
        await RecipeMutationGate.WaitAsync();
        try
        {
            RecipeMutationMutex.WaitOne();
            try
            {
                var recipe = await _recipeRepository.FindByIdAsync(id);
                await _recipeRepository.DeleteByIdAsync(id);

                if (recipe != null)
                {
                    RecipeRealtimeState.QueueSignal($"Recipe removed: {recipe.Title}");
                }
            }
            finally
            {
                RecipeMutationMutex.ReleaseMutex();
            }
        }
        finally
        {
            RecipeMutationGate.Release();
        }
    }

    private static RecipeDTO ToDTO(Recipe recipe) => new(
        recipe.Id,
        recipe.Title,
        recipe.Description,
        recipe.Steps.Select(s => new RecipeStepDTO(s.Title, s.Description)).ToList(),
        recipe.Owner,
        recipe.Category,
        recipe.Created,
        recipe.ShoppingListId
    );
}
