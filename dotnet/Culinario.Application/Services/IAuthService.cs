using Culinario.Application.DTOs;

namespace Culinario.Application.Services;

public interface IAuthService
{
    Task<AuthResponseDTO> LoginAsync(LoginRequestDTO request);
    Task LogoutAsync(string username);
    Task<AuthResponseDTO> RefreshAsync(RefreshTokenRequest request);
}
