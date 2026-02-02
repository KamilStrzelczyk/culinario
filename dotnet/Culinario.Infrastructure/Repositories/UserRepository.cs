using Culinario.Domain.Models;
using Culinario.Domain.Repositories;
using Culinario.Infrastructure.Persistence;
using Culinario.Infrastructure.Persistence.Entities;
using Microsoft.EntityFrameworkCore;

namespace Culinario.Infrastructure.Repositories;

public class UserRepository : IUserRepository
{
    private readonly CulinarioDbContext _context;

    public UserRepository(CulinarioDbContext context)
    {
        _context = context;
    }

    public async Task<List<User>> FindAllAsync()
    {
        var entities = await _context.Users.ToListAsync();
        return entities.Select(ToDomain).ToList();
    }

    public async Task<User?> FindByIdAsync(long id)
    {
        var entity = await _context.Users.FindAsync(id);
        return entity == null ? null : ToDomain(entity);
    }

    public async Task<User> SaveAsync(User user)
    {
        var entity = ToEntity(user);
        if (entity.Id == null)
        {
            _context.Users.Add(entity);
        }
        else
        {
            _context.Users.Update(entity);
        }
        await _context.SaveChangesAsync();
        return ToDomain(entity);
    }

    public async Task DeleteByIdAsync(long id)
    {
        var entity = await _context.Users.FindAsync(id);
        if (entity != null)
        {
            _context.Users.Remove(entity);
            await _context.SaveChangesAsync();
        }
    }

    public async Task<User?> FindByUsernameAsync(string username)
    {
        var entity = await _context.Users.FirstOrDefaultAsync(u => u.Username == username);
        return entity == null ? null : ToDomain(entity);
    }

    // Proste mappery (można wydzielić do AutoMapper)
    private static User ToDomain(UserEntity entity) => new()
    {
        Id = entity.Id,
        Username = entity.Username,
        Email = entity.Email,
        Password = entity.Password
    };

    private static UserEntity ToEntity(User domain) => new()
    {
        Id = domain.Id,
        Username = domain.Username,
        Email = domain.Email,
        Password = domain.Password
    };
}
