using Culinario.Application.DTOs;

namespace Culinario.Application.Services;

public interface IIngredientService
{
    Task<List<IngredientDTO>> GetIngredientsForRecipeAsync(int recipeId);
    Task<List<string>> FindRecipeTitlesContainingIngredientAsync(string ingredientName);
    Task<IngredientDTO> CreateOrGetIngredientAsync(string ingredientName);
}
