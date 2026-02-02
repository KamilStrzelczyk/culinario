using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Culinario.Domain.Models;
using Culinario.Domain.Repositories;

namespace Culinario.Infrastructure.Services;

public class ShoppingListService : IShoppingListService
{
    private readonly IShoppingListRepository _repository;

    public ShoppingListService(IShoppingListRepository repository)
    {
        _repository = repository;
    }

    public async Task<List<ShoppingListDTO>> GetAllAsync()
    {
        var lists = await _repository.FindAllAsync();
        return lists.Select(ToDTO).ToList();
    }

    public async Task<ShoppingListDTO> GetAsync(int id)
    {
        var list = await _repository.FindByIdAsync(id);
        if (list == null) throw new Exception($"Shopping list not found with id: {id}");
        return ToDTO(list);
    }

    public async Task CreateAsync(NewShoppingListDTO dto)
    {
        var list = new ShoppingList
        {
            Title = dto.Title,
            Description = dto.Description,
            Items = dto.Items.Select(i => new ShoppingListItem { Name = i.Name, Amount = i.Amount }).ToList()
        };
        await _repository.SaveAsync(list);
    }

    public async Task DeleteAsync(int id)
    {
        await _repository.DeleteByIdAsync(id);
    }

    private static ShoppingListDTO ToDTO(ShoppingList l) => new(
        l.Id,
        l.Title,
        l.Description,
        l.Items.Select(i => new ShoppingListItemDTO(i.Name, i.Amount)).ToList()
    );
}
