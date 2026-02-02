namespace Culinario.Application.DTOs;

public record RecipeDTO(
    int Id,
    string Title,
    string Description,
    List<RecipeStepDTO> Steps,
    string Owner,
    string Category,
    string Created,
    int? ShoppingListId
);

public record NewRecipeDTO(
    string Title,
    string Description,
    List<RecipeStepDTO> Steps,
    string Category,
    NewShoppingListDTO? ShoppingList
)
{
    public NewRecipeDTO() : this(string.Empty, string.Empty, new List<RecipeStepDTO>(), string.Empty, null) { }
}

public record RecipeStepDTO(string Title, string Description)
{
    public RecipeStepDTO() : this(string.Empty, string.Empty) { }
}
