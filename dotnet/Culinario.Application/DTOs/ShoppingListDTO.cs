namespace Culinario.Application.DTOs;

public record ShoppingListDTO(
    int Id,
    string Title,
    string Description,
    List<ShoppingListItemDTO> Items
);

public record NewShoppingListDTO(
    string Title,
    string Description,
    List<ShoppingListItemDTO> Items
);

public record ShoppingListItemDTO(string Name, int Amount);
