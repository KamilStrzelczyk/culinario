using System.Text.Json;
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
        var entities = await _context.ShoppingLists.ToListAsync();
        return entities.Select(ToDomain).ToList();
    }

    public async Task<ShoppingList?> FindByIdAsync(int id)
    {
        var entity = await _context.ShoppingLists.FindAsync(id);
        return entity == null ? null : ToDomain(entity);
    }

    public async Task<ShoppingList> SaveAsync(ShoppingList shoppingList)
    {
        var entity = ToEntity(shoppingList);
        if (entity.Id == null || entity.Id == 0)
        {
            _context.ShoppingLists.Add(entity);
        }
        else
        {
            _context.ShoppingLists.Update(entity);
        }
        await _context.SaveChangesAsync();
        return ToDomain(entity);
    }

    public async Task DeleteByIdAsync(int id)
    {
        var entity = await _context.ShoppingLists.FindAsync(id);
        if (entity != null)
        {
            _context.ShoppingLists.Remove(entity);
            await _context.SaveChangesAsync();
        }
    }

    private static ShoppingList ToDomain(ShoppingListEntity entity)
    {
        var items = string.IsNullOrWhiteSpace(entity.ItemsJson)
            ? new List<ShoppingListItem>()
            : JsonSerializer.Deserialize<List<ShoppingListItem>>(entity.ItemsJson) ?? new List<ShoppingListItem>();

        return new ShoppingList
        {
            Id = entity.Id ?? 0,
            Title = entity.Title,
            Description = entity.Description,
            Items = items
        };
    }

    private static ShoppingListEntity ToEntity(ShoppingList domain) => new()
    {
        Id = domain.Id == 0 ? null : domain.Id,
        Title = domain.Title,
        Description = domain.Description,
        ItemsJson = JsonSerializer.Serialize(domain.Items)
    };
}
