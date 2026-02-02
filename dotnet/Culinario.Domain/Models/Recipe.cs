namespace Culinario.Domain.Models;

public class Recipe
{
    public int Id { get; set; }
    public string Title { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public List<RecipeStep> Steps { get; set; } = new(); // Zmiana na listę
    public string Owner { get; set; } = string.Empty;
    public string Category { get; set; } = string.Empty;
    public string Created { get; set; } = string.Empty;
    public int? ShoppingListId { get; set; }
}

public class RecipeStep
{
    public string Title { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
}
