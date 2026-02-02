using Culinario.Domain.Models;

namespace Culinario.Domain.Repositories;

public interface IShoppingListRepository
{
    Task<List<ShoppingList>> FindAllAsync();
    Task<ShoppingList?> FindByIdAsync(int id);
    Task<ShoppingList> SaveAsync(ShoppingList shoppingList); // Zmiana na zwrot obiektu
    Task DeleteByIdAsync(int id);
}
