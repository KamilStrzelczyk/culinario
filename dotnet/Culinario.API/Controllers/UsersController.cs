using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Microsoft.AspNetCore.Mvc;

namespace Culinario.API.Controllers;

[ApiController]
[Route("api/[controller]")]
public class UsersController : ControllerBase
{
    private readonly IUserService _userService;

    public UsersController(IUserService userService)
    {
        _userService = userService;
    }

    [HttpGet]
    public async Task<ActionResult<List<UserDTO>>> GetAll()
    {
        return await _userService.GetAllUsersAsync();
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<UserDTO>> Get(long id)
    {
        try
        {
            return await _userService.GetUserAsync(id);
        }
        catch (Exception)
        {
            return NotFound();
        }
    }

    [HttpPost]
    public async Task<ActionResult<UserDTO>> Create(NewUserDTO dto)
    {
        var user = await _userService.CreateUserAsync(dto);
        return CreatedAtAction(nameof(Get), new { id = user.Id }, user);
    }

    [HttpPut("{id}")]
    public async Task<ActionResult<UserDTO>> Update(long id, UserDTO dto)
    {
        if (id != dto.Id) return BadRequest();
        return await _userService.UpdateUserAsync(dto);
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> Delete(long id)
    {
        await _userService.DeleteUserAsync(id);
        return NoContent();
    }
}
