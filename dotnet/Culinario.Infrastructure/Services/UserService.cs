using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Culinario.Domain.Models;
using Culinario.Domain.Repositories;

namespace Culinario.Infrastructure.Services;

public class UserService : IUserService
{
    private readonly IUserRepository _userRepository;

    public UserService(IUserRepository userRepository)
    {
        _userRepository = userRepository;
    }

    public async Task<List<UserDTO>> GetAllUsersAsync()
    {
        var users = await _userRepository.FindAllAsync();
        return users.Select(u => new UserDTO(u.Id ?? 0, u.Username, u.Email)).ToList();
    }

    public async Task<UserDTO> GetUserAsync(long id)
    {
        var user = await _userRepository.FindByIdAsync(id);
        if (user == null) throw new Exception($"User not found with id: {id}");
        return new UserDTO(user.Id ?? 0, user.Username, user.Email);
    }

    public async Task<UserDTO> CreateUserAsync(NewUserDTO newUserDTO)
    {
        var user = new User
        {
            Username = newUserDTO.Name,
            Email = newUserDTO.Email,
            Password = newUserDTO.Password
        };
        var savedUser = await _userRepository.SaveAsync(user);
        return new UserDTO(savedUser.Id ?? 0, savedUser.Username, savedUser.Email);
    }

    public async Task DeleteUserAsync(long id)
    {
        await _userRepository.DeleteByIdAsync(id);
    }

    public async Task<UserDTO> UpdateUserAsync(UserDTO userDTO)
    {
        var existingUser = await _userRepository.FindByIdAsync(userDTO.Id);
        if (existingUser == null) throw new Exception("User not found");

        existingUser.Username = userDTO.Name;
        existingUser.Email = userDTO.Email;

        var savedUser = await _userRepository.SaveAsync(existingUser);
        return new UserDTO(savedUser.Id ?? 0, savedUser.Username, savedUser.Email);
    }
}
