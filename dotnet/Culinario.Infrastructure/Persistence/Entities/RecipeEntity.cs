using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Culinario.Infrastructure.Persistence.Entities;

[Table("Recipes")]
public class RecipeEntity
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int? Id { get; set; }

    public string Title { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;

    public string StepsJson { get; set; } = string.Empty;

    public string Owner { get; set; } = string.Empty;
    public string Category { get; set; } = string.Empty;
    public string Created { get; set; } = string.Empty;

    public int? ShoppingListId { get; set; }
}
