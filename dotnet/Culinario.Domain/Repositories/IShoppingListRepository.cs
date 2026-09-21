using Culinario.Domain.Models;

namespace Culinario.Domain.Repositories;

public interface IShoppingListRepository
{
    Task<List<ShoppingList>> FindAllAsync();
    Task<ShoppingList?> FindByIdAsync(int id);
    Task<ShoppingList> SaveAsync(ShoppingList shoppingList);
    Task DeleteByIdAsync(int id);
    Task<bool> ContainsItemAsync(int shoppingListId, string itemName);
    Task<List<string>> FindRecipeTitlesContainingIngredientAsync(string ingredientName);
}
