using Culinario.Domain.Models;
using Culinario.Domain.Repositories;
using Culinario.Infrastructure.Persistence;
using Culinario.Infrastructure.Persistence.Entities;
using Microsoft.EntityFrameworkCore;

namespace Culinario.Infrastructure.Repositories;

public class ShoppingListRepository : IShoppingListRepository
{
    private readonly CulinarioDbContext _context;

    public ShoppingListRepository(CulinarioDbContext context)
    {
        _context = context;
    }

    public async Task<List<ShoppingList>> FindAllAsync()
    {
        var entities = await _context.ShoppingLists
            .Include(x => x.Items)
            .ToListAsync();

        return entities.Select(ToDomain).ToList();
    }

    public async Task<ShoppingList?> FindByIdAsync(int id)
    {
        var entity = await _context.ShoppingLists
            .Include(x => x.Items)
            .FirstOrDefaultAsync(x => x.Id == id);

        return entity == null ? null : ToDomain(entity);
    }

    public async Task<ShoppingList> SaveAsync(ShoppingList shoppingList)
    {
        var normalizedItems = NormalizeItems(shoppingList.Items);
        var entity = await _context.ShoppingLists
            .Include(x => x.Items)
            .FirstOrDefaultAsync(x => x.Id == shoppingList.Id);

        if (entity == null)
        {
            entity = new ShoppingListEntity
            {
                Title = shoppingList.Title,
                Description = shoppingList.Description,
                Items = new List<ShoppingListItemEntity>()
            };
            _context.ShoppingLists.Add(entity);
        }
        else
        {
            entity.Title = shoppingList.Title;
            entity.Description = shoppingList.Description;
        }

        var existing = entity.Items.ToDictionary(x => x.NormalizedName, StringComparer.OrdinalIgnoreCase);
        foreach (var item in normalizedItems)
        {
            var normalized = NormalizeName(item.Name);
            if (existing.TryGetValue(normalized, out var existingItem))
            {
                existingItem.Amount = item.Amount;
                existingItem.Name = item.Name.Trim();
                existingItem.NormalizedName = normalized;
                continue;
            }

            entity.Items.Add(new ShoppingListItemEntity
            {
                Name = item.Name.Trim(),
                NormalizedName = normalized,
                Amount = item.Amount,
                ShoppingList = entity
            });
        }

        foreach (var item in entity.Items.ToList())
        {
            if (!normalizedItems.Any(x => NormalizeName(x.Name) == item.NormalizedName))
            {
                _context.ShoppingListItems.Remove(item);
            }
        }

        await _context.SaveChangesAsync();
        return ToDomain(entity);
    }

    public async Task DeleteByIdAsync(int id)
    {
        var entity = await _context.ShoppingLists
            .Include(x => x.Items)
            .FirstOrDefaultAsync(x => x.Id == id);

        if (entity != null)
        {
            _context.ShoppingLists.Remove(entity);
            await _context.SaveChangesAsync();
        }
    }

    public async Task<bool> ContainsItemAsync(int shoppingListId, string itemName)
    {
        var normalized = NormalizeName(itemName);
        return await _context.ShoppingListItems
            .AnyAsync(x => x.ShoppingListId == shoppingListId && x.NormalizedName == normalized);
    }

    public async Task<List<string>> FindRecipeTitlesContainingIngredientAsync(string ingredientName)
    {
        var normalized = NormalizeName(ingredientName);
        var recipes = await _context.Recipes.ToListAsync();

        return recipes
            .Where(recipe => IsIngredientMentioned(recipe.Title, normalized)
                || IsIngredientMentioned(recipe.Description, normalized)
                || IsIngredientMentioned(recipe.StepsJson, normalized)
                || IsIngredientMentioned(recipe.Owner, normalized)
                || IsIngredientMentioned(recipe.Category, normalized))
            .Select(recipe => recipe.Title)
            .Distinct(StringComparer.OrdinalIgnoreCase)
            .ToList();
    }

    private static ShoppingList ToDomain(ShoppingListEntity entity)
    {
        return new ShoppingList
        {
            Id = entity.Id ?? 0,
            Title = entity.Title,
            Description = entity.Description,
            Items = entity.Items
                .Select(x => new ShoppingListItem
                {
                    Id = x.Id ?? 0,
                    ShoppingListId = x.ShoppingListId,
                    Name = x.Name,
                    Amount = x.Amount
                })
                .OrderBy(x => x.Name)
                .ToList()
        };
    }

    private static List<ShoppingListItem> NormalizeItems(IEnumerable<ShoppingListItem> items)
    {
        return items
            .GroupBy(x => NormalizeName(x.Name), StringComparer.OrdinalIgnoreCase)
            .Select(group => new ShoppingListItem
            {
                Name = group.First().Name.Trim(),
                Amount = group.Sum(x => x.Amount),
                ShoppingListId = group.First().ShoppingListId
            })
            .Where(x => !string.IsNullOrWhiteSpace(x.Name))
            .ToList();
    }

    private static string NormalizeName(string value)
    {
        return value.Trim().ToLowerInvariant();
    }

    private static bool IsIngredientMentioned(string value, string ingredient)
    {
        return !string.IsNullOrWhiteSpace(value)
               && value.Contains(ingredient, StringComparison.OrdinalIgnoreCase);
    }
}
