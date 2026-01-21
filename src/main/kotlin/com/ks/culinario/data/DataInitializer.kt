package com.ks.culinario.data

import com.ks.culinario.data.dao.RecipeDao
import com.ks.culinario.data.dao.ShoppingListDao
import com.ks.culinario.data.dao.UserDao
import com.ks.culinario.data.entity.RecipeEntity
import com.ks.culinario.data.entity.ShoppingListEntity
import com.ks.culinario.data.entity.UserEntity
import org.springframework.boot.CommandLineRunner
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DataInitializer(
    private val userDao: UserDao,
    private val recipeDao: RecipeDao,
    private val shoppingListDao: ShoppingListDao,
    private val passwordEncoder: PasswordEncoder
) : CommandLineRunner {

    override fun run(vararg args: String) {
        if (userDao.count() == 0L) {
            println(">>> INICJALIZACJA BAZY DANYCH <<<")

            val admin = UserEntity(
                username = "admin",
                email = "admin@culinario.pl",
                password = passwordEncoder.encode("admin123")!!
            )
            userDao.save(admin)
            println("Utworzono użytkownika: login='admin', hasło='admin123'")

            val shoppingListEntity = ShoppingListEntity(
                title = "Składniki na naleśniki",
                description = "Co potrzeba do puszystych naleśników",
                items = """[{"name":"Mąka","amount":200},{"name":"Mleko","amount":250},{"name":"Jajka","amount":2}]"""
            )
            shoppingListDao.save(shoppingListEntity)
            println("Utworzono listę zakupów: '${shoppingListEntity.title}'")

            val recipeEntity = RecipeEntity(
                title = "Klasyczne naleśniki",
                description = "Prosty przepis na pyszne, domowe naleśniki.",
                stepTitle = "Wymieszaj i usmaż",
                stepDescription = "1. Wymieszaj wszystkie składniki. 2. Smaż na patelni.",
                owner = "admin",
                category = "Śniadanie",
                created = LocalDate.now().toString(),
                shoppingListId = shoppingListEntity.id
            )
            recipeDao.save(recipeEntity)
            println("Utworzono przepis: '${recipeEntity.title}'")

            println(">>> BAZA DANYCH ZAINICJALIZOWANA <<<")
        }
    }
}
