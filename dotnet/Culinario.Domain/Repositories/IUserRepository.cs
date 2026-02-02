using Culinario.Domain.Models;

namespace Culinario.Domain.Repositories;

public interface IUserRepository
{
    Task<List<User>> FindAllAsync();
    Task<User?> FindByIdAsync(long id);
    Task<User> SaveAsync(User user);
    Task DeleteByIdAsync(long id);
    Task<User?> FindByUsernameAsync(string username);
}
