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
)
{

    public NewShoppingListDTO() : this(string.Empty, string.Empty, new List<ShoppingListItemDTO>()) { }
}

public record ShoppingListItemDTO(string Name, int Amount)
{
    public ShoppingListItemDTO() : this(string.Empty, 0) { }
}
