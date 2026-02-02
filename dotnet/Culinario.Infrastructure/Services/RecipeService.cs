using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Culinario.Domain.Models;
using Culinario.Domain.Repositories;

namespace Culinario.Infrastructure.Services;

public class RecipeService : IRecipeService
{
    private readonly IRecipeRepository _recipeRepository;
    private readonly IShoppingListRepository _shoppingListRepository;

    public RecipeService(IRecipeRepository recipeRepository, IShoppingListRepository shoppingListRepository)
    {
        _recipeRepository = recipeRepository;
        _shoppingListRepository = shoppingListRepository;
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
            Owner = ownerUsername, // Używamy nazwy zalogowanego użytkownika
            Category = dto.Category,
            Created = DateTime.Now.ToString("yyyy-MM-dd"),
            ShoppingListId = shoppingListId
        };
        await _recipeRepository.SaveAsync(recipe);
    }

    public async Task DeleteAsync(int id)
    {
        await _recipeRepository.DeleteByIdAsync(id);
    }

    private static RecipeDTO ToDTO(Recipe r) => new(
        r.Id,
        r.Title,
        r.Description,
        r.Steps.Select(s => new RecipeStepDTO(s.Title, s.Description)).ToList(),
        r.Owner,
        r.Category,
        r.Created,
        r.ShoppingListId
    );
}
