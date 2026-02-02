using Culinario.Application.DTOs;

namespace Culinario.Application.Services;

public interface IUserService
{
    Task<List<UserDTO>> GetAllUsersAsync();
    Task<UserDTO> GetUserAsync(long id);
    Task<UserDTO> CreateUserAsync(NewUserDTO newUserDTO);
    Task DeleteUserAsync(long id);
    Task<UserDTO> UpdateUserAsync(UserDTO userDTO);
}
