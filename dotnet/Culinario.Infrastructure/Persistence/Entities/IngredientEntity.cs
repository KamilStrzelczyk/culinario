using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Culinario.Infrastructure.Persistence.Entities;

[Table("Ingredients")]
public class IngredientEntity
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int? Id { get; set; }

    public string Name { get; set; } = string.Empty;
    public string NormalizedName { get; set; } = string.Empty;
}

[Table("RecipeIngredients")]
public class RecipeIngredientEntity
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int? Id { get; set; }

    public int RecipeId { get; set; }
    public int IngredientId { get; set; }
    public string Name { get; set; } = string.Empty;
    public int Amount { get; set; }
    public string Unit { get; set; } = string.Empty;

    public RecipeEntity Recipe { get; set; } = null!;
    public IngredientEntity Ingredient { get; set; } = null!;
}
