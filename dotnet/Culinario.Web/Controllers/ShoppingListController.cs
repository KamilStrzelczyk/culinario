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

    // GET: ShoppingList
    public async Task<IActionResult> Index()
    {
        var lists = await _shoppingListService.GetAllAsync();
        return View(lists);
    }

    // GET: ShoppingList/Details/5
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

    // GET: ShoppingList/Create
    public IActionResult Create()
    {
        return View(new NewShoppingListDTO());
    }

    // POST: ShoppingList/Create
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

    // POST: ShoppingList/Delete/5
    [HttpPost, ActionName("Delete")]
    [ValidateAntiForgeryToken]
    public async Task<IActionResult> DeleteConfirmed(int id)
    {
        await _shoppingListService.DeleteAsync(id);
        return RedirectToAction(nameof(Index));
    }
}
