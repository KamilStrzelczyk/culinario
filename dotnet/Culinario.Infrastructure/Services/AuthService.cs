using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Culinario.Domain.Repositories;

namespace Culinario.Infrastructure.Services;

public class AuthService : IAuthService
{
    private readonly IUserRepository _userRepository;

    public AuthService(IUserRepository userRepository)
    {
        _userRepository = userRepository;
    }

    public async Task<AuthResponseDTO> LoginAsync(LoginRequestDTO request)
    {
        var user = await _userRepository.FindByUsernameAsync(request.Username);
        if (user == null || user.Password != request.Password)
        {
            throw new Exception("Invalid credentials");
        }

        return new AuthResponseDTO("fake-jwt-token", "fake-refresh-token");
    }

    public Task LogoutAsync(string username)
    {
        return Task.CompletedTask;
    }

    public Task<AuthResponseDTO> RefreshAsync(RefreshTokenRequest request)
    {
        return Task.FromResult(new AuthResponseDTO("new-fake-jwt", "new-fake-refresh"));
    }
}
