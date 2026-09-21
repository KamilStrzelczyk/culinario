namespace Culinario.Application.DTOs;

public record IngredientDTO(
    int Id,
    string Name,
    string NormalizedName
);

public record RecipeIngredientDTO(
    int Id,
    int RecipeId,
    int IngredientId,
    string Name,
    int Amount,
    string Unit
);
