using System.Text.Json;
using Culinario.Domain.Models;
using Culinario.Infrastructure.Persistence.Entities;

namespace Culinario.Infrastructure.Persistence;

public static class DbInitializer
{
    public static void Seed(CulinarioDbContext context)
    {
        context.Database.EnsureCreated();
        if (context.Users.Any()) return;

        var admin = new UserEntity { Username = "admin", Email = "admin@culinario.pl", Password = "admin123" };
        context.Users.Add(admin);
        context.SaveChanges();

       
        var pizzaShoppingList = new ShoppingListEntity
        {
            Title = "Składniki na pizzę",
            Description = "Zakupy na piątkowy wieczór",
            ItemsJson = JsonSerializer.Serialize(new List<ShoppingListItem>
            {
                new() { Name = "Mąka pszenna typ 00", Amount = 500 },
                new() { Name = "Ser mozzarella", Amount = 300 },
                new() { Name = "Sos pomidorowy", Amount = 1 },
                new() { Name = "Drożdże", Amount = 25 }
            })
        };
        context.ShoppingLists.Add(pizzaShoppingList);

        
        var applePieShoppingList = new ShoppingListEntity
        {
            Title = "Składniki na szarlotkę",
            Description = "Na pyszne, domowe ciasto",
            ItemsJson = JsonSerializer.Serialize(new List<ShoppingListItem>
            {
                new() { Name = "Jabłka (szara reneta)", Amount = 1500 },
                new() { Name = "Mąka krupczatka", Amount = 500 },
                new() { Name = "Masło", Amount = 250 },
                new() { Name = "Cukier", Amount = 150 },
                new() { Name = "Cynamon", Amount = 1 }
            })
        };
        context.ShoppingLists.Add(applePieShoppingList);
        context.SaveChanges();

       
        var recipes = new RecipeEntity[]
        {
            
            new()
            {
                Title = "Domowa Pizza Margherita",
                Description = "Najlepsza pizza na cienkim cieście, prosto z pieca.",
                StepsJson = JsonSerializer.Serialize(new List<RecipeStep>
                {
                    new() { Title = "Przygotuj ciasto", Description = "Wymieszaj mąkę, wodę i drożdże. Odstaw do wyrośnięcia na 1 godzinę." },
                    new() { Title = "Przygotuj dodatki", Description = "Nałóż sos pomidorowy i starty ser mozzarella." },
                    new() { Title = "Piecz", Description = "Piecz w 250 stopniach przez 10 minut." }
                }),
                Owner = "admin",
                Category = "Obiad",
                Created = DateTime.Now.ToString("yyyy-MM-dd"),
                ShoppingListId = pizzaShoppingList.Id
            },
        
            new()
            {
                Title = "Tradycyjna Szarlotka",
                Description = "Kruche ciasto z soczystymi jabłkami i cynamonem.",
                StepsJson = JsonSerializer.Serialize(new List<RecipeStep>
                {
                    new() { Title = "Zagnieć ciasto", Description = "Z mąki, masła i cukru zagnieć kruche ciasto. Podziel na dwie części." },
                    new() { Title = "Przygotuj jabłka", Description = "Jabłka obierz, zetrzyj na tarce i podsmaż z cynamonem." },
                    new() { Title = "Złóż i piecz", Description = "Wyłóż formę jedną częścią ciasta, nałóż jabłka, przykryj drugą częścią. Piecz 50 minut w 180 stopniach." }
                }),
                Owner = "admin",
                Category = "Deser",
                Created = DateTime.Now.AddDays(-2).ToString("yyyy-MM-dd"),
                ShoppingListId = applePieShoppingList.Id
            },
            
            new()
            {
                Title = "Spaghetti Carbonara",
                Description = "Klasyk kuchni włoskiej bez śmietany! Prawdziwa Carbonara.",
                StepsJson = JsonSerializer.Serialize(new List<RecipeStep>
                {
                    new() { Title = "Ugotuj makaron", Description = "Ugotuj makaron spaghetti al dente w osolonej wodzie." },
                    new() { Title = "Przygotuj sos", Description = "Wymieszaj żółtka z serem pecorino i dużą ilością świeżo mielonego pieprzu." },
                    new() { Title = "Podsmaż guanciale", Description = "Pokrojone w kostkę guanciale (lub boczek) smaż na patelni, aż będzie chrupiące." },
                    new() { Title = "Połącz składniki", Description = "Odcedzony makaron wrzuć na patelnię z guanciale. Zdejmij z ognia, dodaj masę jajeczną i energicznie mieszaj, dodając odrobinę wody z makaronu, aby sos był kremowy." }
                }),
                Owner = "admin",
                Category = "Obiad",
                Created = DateTime.Now.AddDays(-5).ToString("yyyy-MM-dd"),
            },
            
            new()
            {
                Title = "Jajecznica na boczku",
                Description = "Szybkie i sycące śniadanie.",
                StepsJson = JsonSerializer.Serialize(new List<RecipeStep>
                {
                    new() { Title = "Smażenie", Description = "1. Podsmaż boczek na patelni. 2. Wbij jajka. 3. Mieszaj do ścięcia." }
                }),
                Owner = "admin",
                Category = "Śniadanie",
                Created = DateTime.Now.AddDays(-1).ToString("yyyy-MM-dd"),
            },
           
            new()
            {
                Title = "Sałatka Grecka",
                Description = "Lekka i orzeźwiająca sałatka, idealna na lato.",
                StepsJson = JsonSerializer.Serialize(new List<RecipeStep>
                {
                    new() { Title = "Pokrój warzywa", Description = "Pokrój pomidory, ogórka, paprykę i czerwoną cebulę w grubą kostkę." },
                    new() { Title = "Dodaj resztę", Description = "Dodaj ser feta w jednym kawałku, oliwki i polej wszystko oliwą z oliwek. Dopraw oregano." }
                }),
                Owner = "admin",
                Category = "Sałatka",
                Created = DateTime.Now.AddDays(-10).ToString("yyyy-MM-dd"),
            }
        };

        context.Recipes.AddRange(recipes);
        context.SaveChanges();
    }
}
