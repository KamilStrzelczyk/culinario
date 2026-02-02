namespace Culinario.Application.DTOs;

// DTO do odczytu
public record ShoppingListDTO(
    int Id,
    string Title,
    string Description,
    List<ShoppingListItemDTO> Items
);

// DTO do tworzenia
public record NewShoppingListDTO(
    string Title,
    string Description,
    List<ShoppingListItemDTO> Items
)
{
    // Konstruktor bezparametrowy potrzebny do bindowania w MVC (formularze)
    public NewShoppingListDTO() : this(string.Empty, string.Empty, new List<ShoppingListItemDTO>()) { }
}

public record ShoppingListItemDTO(string Name, int Amount)
{
    public ShoppingListItemDTO() : this(string.Empty, 0) { }
}
