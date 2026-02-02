using Culinario.Infrastructure.Persistence.Entities;
using Microsoft.EntityFrameworkCore;

namespace Culinario.Infrastructure.Persistence;

public class CulinarioDbContext : DbContext
{
    public CulinarioDbContext(DbContextOptions<CulinarioDbContext> options) : base(options) { }

    public DbSet<UserEntity> Users { get; set; }
    public DbSet<RecipeEntity> Recipes { get; set; }
    public DbSet<ShoppingListEntity> ShoppingLists { get; set; }
    public DbSet<TokenEntity> Tokens { get; set; }
}
