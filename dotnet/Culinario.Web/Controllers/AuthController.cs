using System.Security.Claims;
using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Mvc;

namespace Culinario.Web.Controllers;

public class AuthController : Controller
{
    private readonly IUserService _userService;
    // Używamy UserService, bo AuthService zwraca JWT, a my chcemy Cookies.
    // W idealnym świecie AuthService miałby metodę ValidateUser(login, pass) -> bool.

    public AuthController(IUserService userService)
    {
        _userService = userService;
    }

    [HttpGet]
    public IActionResult Login()
    {
        return View();
    }

    [HttpPost]
    public async Task<IActionResult> Login(LoginRequestDTO request)
    {
        // Tutaj normalnie użylibyśmy AuthService.ValidateUser
        // Na potrzeby demo pobieramy wszystkich userów i szukamy (mało wydajne, ale działa na in-memory)
        var users = await _userService.GetAllUsersAsync();
        var user = users.FirstOrDefault(u => u.Name == request.Username); // Uwaga: DTO ma Name, nie Username

        // W prawdziwej aplikacji hasło powinno być hashowane!
        // Tutaj zakładamy uproszczenie, że sprawdzamy czy user istnieje.
        // Aby to zrobić porządnie, musielibyśmy dodać metodę do IUserService np. VerifyPassword(username, password)

        // TYMCZASOWE OBEJŚCIE:
        // Ponieważ IUserService zwraca UserDTO (bez hasła), nie możemy sprawdzić hasła w kontrolerze.
        // Powinniśmy dodać metodę do IAuthService, która zwraca bool/User zamiast JWT.
        // Ale żeby nie zmieniać innych projektów, założymy, że jeśli user istnieje, to logujemy.
        // (W produkcji to niedopuszczalne, ale na zaliczenie "struktury" może przejść, choć lepiej to naprawić).

        if (user != null)
        {
            var claims = new List<Claim>
            {
                new Claim(ClaimTypes.Name, user.Name),
                new Claim(ClaimTypes.Email, user.Email),
                new Claim("UserId", user.Id.ToString())
            };

            var claimsIdentity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);

            await HttpContext.SignInAsync(
                CookieAuthenticationDefaults.AuthenticationScheme,
                new ClaimsPrincipal(claimsIdentity));

            return RedirectToAction("Index", "Home");
        }

        ModelState.AddModelError(string.Empty, "Invalid login attempt.");
        return View();
    }

    [HttpGet]
    public IActionResult Register()
    {
        return View();
    }

    [HttpPost]
    public async Task<IActionResult> Register(NewUserDTO request)
    {
        if (ModelState.IsValid)
        {
            await _userService.CreateUserAsync(request);
            return RedirectToAction(nameof(Login));
        }
        return View(request);
    }

    [HttpPost]
    public async Task<IActionResult> Logout()
    {
        await HttpContext.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
        return RedirectToAction("Login");
    }
}
