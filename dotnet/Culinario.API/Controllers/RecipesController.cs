using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Microsoft.AspNetCore.Mvc;

namespace Culinario.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class RecipesController : ControllerBase
{
    private readonly IRecipeService _recipeService;

    public RecipesController(IRecipeService recipeService)
    {
        _recipeService = recipeService;
    }

    [HttpGet("all")]
    public async Task<ActionResult<List<RecipeDTO>>> GetAll()
    {
        return await _recipeService.GetAllAsync();
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<RecipeDTO>> Get(int id)
    {
        try
        {
            return await _recipeService.GetAsync(id);
        }
        catch (Exception)
        {
            return NotFound();
        }
    }

    [HttpPost]
    public async Task<IActionResult> Create(NewRecipeDTO dto)
    {
        await _recipeService.CreateAsync(dto);
        return Ok();
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(int id)
    {
        await _recipeService.DeleteAsync(id);
        return NoContent();
    }
}
