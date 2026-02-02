namespace Culinario.Application.DTOs;

public record RecipeDTO(
    int Id,
    string Title,
    string Description,
    RecipeStepDTO Step,
    string Owner,
    string Category,
    string Created,
    int ShoppingListId
);

public record NewRecipeDTO(
    string Title,
    string Description,
    RecipeStepDTO Step,
    string Owner,
    string Category,
    int ShoppingListId
);

public record RecipeStepDTO(string Title, string Description);
