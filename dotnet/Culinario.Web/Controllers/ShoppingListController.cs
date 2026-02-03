using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;

namespace Culinario.Web.Controllers;

[Authorize]
public class ShoppingListController : Controller
{
    private readonly IShoppingListService _shoppingListService;

    public ShoppingListController(IShoppingListService shoppingListService)
    {
        _shoppingListService = shoppingListService;
    }

    public async Task<IActionResult> Index()
    {
        var lists = await _shoppingListService.GetAllAsync();
        return View(lists);
    }

    public async Task<IActionResult> Details(int id)
    {
        try
        {
            var list = await _shoppingListService.GetAsync(id);
            return View(list);
        }
        catch
        {
            return NotFound();
        }
    }

    public IActionResult Create()
    {
        return View(new NewShoppingListDTO());
    }

    [HttpPost]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> Create(NewShoppingListDTO list)
    {
        if (ModelState.IsValid)
        {
            await _shoppingListService.CreateAsync(list);
            return RedirectToAction(nameof(Index));
        }
        return View(list);
    }

    [HttpPost, ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> DeleteConfirmed(int id)
    {
        await _shoppingListService.DeleteAsync(id);
        return RedirectToAction(nameof(Index));
    }
}
