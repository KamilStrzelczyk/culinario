namespace Culinario.Domain.Models;

public class ShoppingList
{
    public int Id { get; set; }
    public string Title { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public List<ShoppingListItem> Items { get; set; } = new();
}

public class ShoppingListItem
{
    public string Name { get; set; } = string.Empty;
    public int Amount { get; set; }
}
