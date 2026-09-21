using Culinario.Infrastructure.Persistence.Entities;
using Microsoft.EntityFrameworkCore;

namespace Culinario.Infrastructure.Persistence;

public class CulinarioDbContext : DbContext
{
    public CulinarioDbContext(DbContextOptions<CulinarioDbContext> options) : base(options) { }

    public DbSet<UserEntity> Users { get; set; }
    public DbSet<RecipeEntity> Recipes { get; set; }
    public DbSet<RecipeIngredientEntity> RecipeIngredients { get; set; }
    public DbSet<IngredientEntity> Ingredients { get; set; }
    public DbSet<ShoppingListEntity> ShoppingLists { get; set; }
    public DbSet<ShoppingListItemEntity> ShoppingListItems { get; set; }
    public DbSet<TokenEntity> Tokens { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<RecipeEntity>()
            .HasMany<RecipeIngredientEntity>()
            .WithOne(x => x.Recipe)
            .HasForeignKey(x => x.RecipeId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<IngredientEntity>()
            .HasIndex(x => x.NormalizedName)
            .IsUnique();

        modelBuilder.Entity<RecipeIngredientEntity>()
            .HasIndex(x => new { x.RecipeId, x.IngredientId })
            .IsUnique();

        modelBuilder.Entity<ShoppingListEntity>()
            .HasMany(x => x.Items)
            .WithOne(x => x.ShoppingList)
            .HasForeignKey(x => x.ShoppingListId)
            .OnDelete(DeleteBehavior.Cascade);

        modelBuilder.Entity<ShoppingListItemEntity>()
            .HasIndex(x => new { x.ShoppingListId, x.NormalizedName })
            .IsUnique();
    }
}
