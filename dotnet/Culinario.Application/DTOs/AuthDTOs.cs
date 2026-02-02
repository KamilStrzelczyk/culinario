namespace Culinario.Application.DTOs;

public record LoginRequestDTO(string Username, string Password);

public record AuthResponseDTO(string AccessToken, string RefreshToken);

public record RefreshTokenRequest(string RefreshToken);
