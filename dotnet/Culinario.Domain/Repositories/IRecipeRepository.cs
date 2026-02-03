using Culinario.Domain.Models;

namespace Culinario.Domain.Repositories;

public interface IRecipeRepository
{
    Task<List<Recipe>> FindAllAsync();
    Task<Recipe?> FindByIdAsync(int id);
    Task SaveAsync(Recipe recipe);
    Task DeleteByIdAsync(int id);
}
