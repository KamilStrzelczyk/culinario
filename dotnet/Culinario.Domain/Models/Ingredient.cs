namespace Culinario.Domain.Models;

public class Ingredient
{
    public int Id { get; set; }
    public string Name { get; set; } = string.Empty;
    public string NormalizedName { get; set; } = string.Empty;
}

public class RecipeIngredient
{
    public int Id { get; set; }
    public int RecipeId { get; set; }
    public int IngredientId { get; set; }
    public string Name { get; set; } = string.Empty;
    public int Amount { get; set; }
    public string Unit { get; set; } = string.Empty;
}
