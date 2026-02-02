using Culinario.Domain.Models;

namespace Culinario.Domain.Repositories;

public interface IShoppingListRepository
{
    Task<List<ShoppingList>> FindAllAsync();
    Task<ShoppingList?> FindByIdAsync(int id);
    Task SaveAsync(ShoppingList shoppingList);
    Task DeleteByIdAsync(int id);
}
