using Culinario.Application.DTOs;

namespace Culinario.Application.Services;

public interface IRecipeService
{
    Task<List<RecipeDTO>> GetAllAsync();
    Task<RecipeDTO> GetAsync(int id);
    Task CreateAsync(NewRecipeDTO newRecipeDTO, string ownerUsername); 
    Task DeleteAsync(int id);
}
