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

    // GET: Recipes
    public async Task<IActionResult> Index()
    {
        var recipes = await _recipeService.GetAllAsync();
        return View(recipes);
    }

    // GET: Recipes/Details/5
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

    // GET: Recipes/Create
    public IActionResult Create()
    {
        return View();
    }

    // POST: Recipes/Create
    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> Create(NewRecipeDTO recipe)
    {
        if (ModelState.IsValid)
        {
            await _recipeService.CreateAsync(recipe);
            return RedirectToAction(nameof(Index));
        }
        return View(recipe);
    }

    // POST: Recipes/Delete/5
    [HttpPost, ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> DeleteConfirmed(int id)
    {
        await _recipeService.DeleteAsync(id);
        return RedirectToAction(nameof(Index));
    }
}
