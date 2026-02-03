using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Microsoft.AspNetCore.Mvc;

namespace Culinario.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AuthController : ControllerBase
{
    private readonly IAuthService _authService;

    public AuthController(IAuthService authService)
    {
        _authService = authService;
    }

    [HttpPost("login")]
    public async Task<ActionResult<AuthResponseDTO>> Login(LoginRequestDTO request)
    {
        try
        {
            return await _authService.LoginAsync(request);
        }
        catch (Exception ex)
        {
            return Unauthorized(ex.Message);
        }
    }

    [HttpPost("refresh")]
    public async Task<ActionResult<AuthResponseDTO>> Refresh(RefreshTokenRequest request)
    {
        return await _authService.RefreshAsync(request);
    }

    [HttpPost("logout")]
    public async Task<IActionResult> Logout()
    {

        var username = User.Identity?.Name;
        if (string.IsNullOrEmpty(username))
        {
            return Unauthorized();
        }
        await _authService.LogoutAsync(username);
        return Ok();
    }
}
