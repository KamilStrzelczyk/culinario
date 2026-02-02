using Culinario.Domain.Models;

namespace Culinario.Domain.Repositories;

public interface IRecipeRepository
{
    Task<List<Recipe>> FindAllAsync();
    Task<Recipe?> FindByIdAsync(int id);
    Task SaveAsync(Recipe recipe); // Tutaj void (Task), zgodnie z Twoim ostatnim życzeniem w Kotlinie
    Task DeleteByIdAsync(int id);
}
