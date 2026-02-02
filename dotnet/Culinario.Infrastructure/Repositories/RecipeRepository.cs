using Culinario.Domain.Models;
using Culinario.Domain.Repositories;
using Culinario.Infrastructure.Persistence;
using Culinario.Infrastructure.Persistence.Entities;
using Microsoft.EntityFrameworkCore;

namespace Culinario.Infrastructure.Repositories;

public class RecipeRepository : IRecipeRepository
{
    private readonly CulinarioDbContext _context;

    public RecipeRepository(CulinarioDbContext context)
    {
        _context = context;
    }

    public async Task<List<Recipe>> FindAllAsync()
    {
        var entities = await _context.Recipes.ToListAsync();
        return entities.Select(ToDomain).ToList();
    }

    public async Task<Recipe?> FindByIdAsync(int id)
    {
        var entity = await _context.Recipes.FindAsync(id);
        return entity == null ? null : ToDomain(entity);
    }

    public async Task SaveAsync(Recipe recipe)
    {
        var entity = ToEntity(recipe);
        if (entity.Id == null || entity.Id == 0)
        {
            _context.Recipes.Add(entity);
        }
        else
        {
            _context.Recipes.Update(entity);
        }
        await _context.SaveChangesAsync();
    }

    public async Task DeleteByIdAsync(int id)
    {
        var entity = await _context.Recipes.FindAsync(id);
        if (entity != null)
        {
            _context.Recipes.Remove(entity);
            await _context.SaveChangesAsync();
        }
    }

    private static Recipe ToDomain(RecipeEntity entity) => new()
    {
        Id = entity.Id ?? 0,
        Title = entity.Title,
        Description = entity.Description,
        Step = new RecipeStep { Title = entity.StepTitle, Description = entity.StepDescription },
        Owner = entity.Owner,
        Category = entity.Category,
        Created = entity.Created,
        ShoppingListId = entity.ShoppingListId
    };

    private static RecipeEntity ToEntity(Recipe domain) => new()
    {
        Id = domain.Id == 0 ? null : domain.Id,
        Title = domain.Title,
        Description = domain.Description,
        StepTitle = domain.Step.Title,
        StepDescription = domain.Step.Description,
        Owner = domain.Owner,
        Category = domain.Category,
        Created = domain.Created,
        ShoppingListId = domain.ShoppingListId
    };
}
