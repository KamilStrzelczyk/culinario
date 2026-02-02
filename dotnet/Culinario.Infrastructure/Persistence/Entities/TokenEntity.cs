using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace Culinario.Infrastructure.Persistence.Entities;

public enum TokenType
{
    ACCESS,
    REFRESH
}

[Table("Tokens")]
public class TokenEntity
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long? Id { get; set; }

    [Required]
    public string Token { get; set; } = string.Empty;

    [Required]
    public TokenType TokenType { get; set; }

    public long? UserId { get; set; }

    [ForeignKey("UserId")]
    public UserEntity? User { get; set; }

    [Required]
    public DateTime ExpiryDate { get; set; }

    public bool Revoked { get; set; }
}
