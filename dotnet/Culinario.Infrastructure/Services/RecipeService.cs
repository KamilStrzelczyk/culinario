using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Culinario.Domain.Models;
using Culinario.Domain.Repositories;

namespace Culinario.Infrastructure.Services;

public class RecipeService : IRecipeService
{
    private readonly IRecipeRepository _recipeRepository;

    public RecipeService(IRecipeRepository recipeRepository)
    {
        _recipeRepository = recipeRepository;
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

    public async Task CreateAsync(NewRecipeDTO dto)
    {
        var recipe = new Recipe
        {
            Title = dto.Title,
            Description = dto.Description,
            Step = new RecipeStep { Title = dto.Step.Title, Description = dto.Step.Description },
            Owner = dto.Owner,
            Category = dto.Category,
            Created = DateTime.Now.ToString("yyyy-MM-dd"),
            ShoppingListId = dto.ShoppingListId
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
        new RecipeStepDTO(r.Step.Title, r.Step.Description),
        r.Owner,
        r.Category,
        r.Created,
        r.ShoppingListId ?? 0
    );
}
