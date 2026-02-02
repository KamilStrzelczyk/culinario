using Culinario.Infrastructure.Persistence.Entities;

namespace Culinario.Infrastructure.Persistence;

public static class DbInitializer
{
    public static void Seed(CulinarioDbContext context)
    {
        // Upewnij się, że baza jest utworzona
        context.Database.EnsureCreated();

        // Sprawdź, czy są już jacyś użytkownicy
        if (context.Users.Any())
        {
            return;   // Baza została już zasiana
        }

        // 1. Dodaj użytkownika
        var admin = new UserEntity
        {
            Username = "admin",
            Email = "admin@culinario.pl",
            Password = "admin123" // W prawdziwej aplikacji: Hash!
        };
        context.Users.Add(admin);
        context.SaveChanges();

        // 2. Dodaj listę zakupów
        var shoppingList = new ShoppingListEntity
        {
            Title = "Składniki na pizzę",
            Description = "Zakupy na piątkowy wieczór",
            ItemsJson = "[{\"Name\":\"Mąka\",\"Amount\":500},{\"Name\":\"Ser\",\"Amount\":300},{\"Name\":\"Sos pomidorowy\",\"Amount\":1}]"
        };
        context.ShoppingLists.Add(shoppingList);
        context.SaveChanges();

        // 3. Dodaj przepisy
        var recipes = new RecipeEntity[]
        {
            new RecipeEntity
            {
                Title = "Domowa Pizza",
                Description = "Najlepsza pizza na cienkim cieście.",
                StepTitle = "Wyrabianie i pieczenie",
                StepDescription = "1. Wyrób ciasto z mąki, wody i drożdży.\n2. Odstaw do wyrośnięcia.\n3. Nałóż sos i ser.\n4. Piecz w 250 stopniach przez 10 minut.",
                Owner = "admin",
                Category = "Obiad",
                Created = DateTime.Now.ToString("yyyy-MM-dd"),
                ShoppingListId = shoppingList.Id
            },
            new RecipeEntity
            {
                Title = "Jajecznica na boczku",
                Description = "Szybkie i sycące śniadanie.",
                StepTitle = "Smażenie",
                StepDescription = "1. Podsmaż boczek na patelni.\n2. Wbij jajka.\n3. Mieszaj do ścięcia.",
                Owner = "admin",
                Category = "Śniadanie",
                Created = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd"),
                ShoppingListId = null
            },
            new RecipeEntity
            {
                Title = "Spaghetti Carbonara",
                Description = "Klasyk kuchni włoskiej bez śmietany!",
                StepTitle = "Gotowanie",
                StepDescription = "1. Ugotuj makaron al dente.\n2. Wymieszaj żółtka z serem pecorino i pieprzem.\n3. Podsmaż guanciale.\n4. Wymieszaj wszystko z odrobiną wody z makaronu.",
                Owner = "admin",
                Category = "Obiad",
                Created = DateTime.Now.AddDays(-5).ToString("yyyy-MM-dd"),
                ShoppingListId = null
            }
        };

        context.Recipes.AddRange(recipes);
        context.SaveChanges();
    }
}
