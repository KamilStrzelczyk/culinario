using Culinario.Application.DTOs;
using Culinario.Application.Services;
using Culinario.Domain.Models;
using Culinario.Infrastructure.Persistence;
using Culinario.Infrastructure.Persistence.Entities;
using Microsoft.EntityFrameworkCore;

namespace Culinario.Infrastructure.Services;

public class IngredientService : IIngredientService
{
    private readonly CulinarioDbContext _context;

    public IngredientService(CulinarioDbContext context)
    {
        _context = context;
    }

    public async Task<List<IngredientDTO>> GetIngredientsForRecipeAsync(int recipeId)
    {
        var ingredients = await _context.RecipeIngredients
            .Include(x => x.Ingredient)
            .Where(x => x.RecipeId == recipeId)
            .Select(x => new IngredientDTO(
                x.Ingredient.Id ?? 0,
                x.Ingredient.Name,
                x.Ingredient.NormalizedName))
            .ToListAsync();

        return ingredients;
    }

    public async Task<List<string>> FindRecipeTitlesContainingIngredientAsync(string ingredientName)
    {
        var normalized = NormalizeName(ingredientName);

        var query = from recipe in _context.Recipes
                    join ri in _context.RecipeIngredients on recipe.Id equals ri.RecipeId into recipeIngredients
                    from ri in recipeIngredients.DefaultIfEmpty()
                    join ing in _context.Ingredients on ri.IngredientId equals ing.Id into ingredients
                    from ing in ingredients.DefaultIfEmpty()
                    where ing != null && ing.NormalizedName == normalized
                    select recipe.Title;

        return await query.Distinct().ToListAsync();
    }

    public async Task<IngredientDTO> CreateOrGetIngredientAsync(string ingredientName)
    {
        var normalized = NormalizeName(ingredientName);
        var ingredient = await _context.Ingredients
            .FirstOrDefaultAsync(x => x.NormalizedName == normalized);

        if (ingredient != null)
        {
            return new IngredientDTO(ingredient.Id ?? 0, ingredient.Name, ingredient.NormalizedName);
        }

        var newIngredient = new IngredientEntity
        {
            Name = ingredientName.Trim(),
            NormalizedName = normalized
        };

        _context.Ingredients.Add(newIngredient);
        await _context.SaveChangesAsync();

        return new IngredientDTO(newIngredient.Id ?? 0, newIngredient.Name, newIngredient.NormalizedName);
    }

    private static string NormalizeName(string value)
    {
        return value.Trim().ToLowerInvariant();
    }
}
