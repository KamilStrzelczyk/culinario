namespace Culinario.Application.DTOs;

public record UserDTO(long Id, string Name, string Email);

public record NewUserDTO(string Name, string Email, string Password);
