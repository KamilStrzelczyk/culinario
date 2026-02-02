using Culinario.Application.DTOs;

namespace Culinario.Application.Services;

public interface IShoppingListService
{
    Task<List<ShoppingListDTO>> GetAllAsync();
    Task<ShoppingListDTO> GetAsync(int id);
    Task CreateAsync(NewShoppingListDTO newShoppingListDTO);
    Task DeleteAsync(int id);
}
