using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Culinario.Web.Controllers;

[Authorize]
public class RecipesController : Controller
{
    private readonly IRecipeService _recipeService;

    public RecipesController(IRecipeService recipeService)
    {
        _recipeService = recipeService;
    }

    public async Task<IActionResult> Index()
    {
        var recipes = await _recipeService.GetAllAsync();
        return View(recipes);
    }

    public async Task<IActionResult> Details(int id)
    {
        try
        {
            var recipe = await _recipeService.GetAsync(id);
            return View(recipe);
        }
        catch
        {
            return NotFound();
        }
    }

    public IActionResult Create()
    {
        return View();
    }

    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> Create(NewRecipeDTO recipe)
    {
        if (ModelState.IsValid)
        {
            var username = User.Identity?.Name;
            if (string.IsNullOrEmpty(username))
            {
                return Unauthorized();
            }
            await _recipeService.CreateAsync(recipe, username);
            return RedirectToAction(nameof(Index));
        }
        return View(recipe);
    }

    [HttpPost, ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> DeleteConfirmed(int id)
    {
        await _recipeService.DeleteAsync(id);
        return RedirectToAction(nameof(Index));
    }
}
