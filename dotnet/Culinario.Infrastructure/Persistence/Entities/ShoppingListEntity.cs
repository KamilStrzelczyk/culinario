using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Culinario.Infrastructure.Persistence.Entities;

[Table("ShoppingLists")]
public class ShoppingListEntity
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public int? Id { get; set; }

    public string Title { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;

    public ICollection<ShoppingListItemEntity> Items { get; set; } = new List<ShoppingListItemEntity>();
}
