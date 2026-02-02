using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Microsoft.AspNetCore.Mvc;

namespace Culinario.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ShoppingListController : ControllerBase
{
    private readonly IShoppingListService _shoppingListService;

    public ShoppingListController(IShoppingListService shoppingListService)
    {
        _shoppingListService = shoppingListService;
    }

    [HttpGet("all")]
    public async Task<ActionResult<List<ShoppingListDTO>>> GetAll()
    {
        return await _shoppingListService.GetAllAsync();
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<ShoppingListDTO>> Get(int id)
    {
        try
        {
            return await _shoppingListService.GetAsync(id);
        }
        catch (Exception)
        {
            return NotFound();
        }
    }

    [HttpPost]
    public async Task<IActionResult> Create(NewShoppingListDTO dto)
    {
        await _shoppingListService.CreateAsync(dto);
        return Ok();
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(int id)
    {
        await _shoppingListService.DeleteAsync(id);
        return NoContent();
    }
}
