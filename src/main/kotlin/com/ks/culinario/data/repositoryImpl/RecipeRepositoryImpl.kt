package com.ks.culinario.data.repositoryImpl

import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.domain.repository.RecipeRepository
import com.ks.culinario.domain.repository.ShoppingListRepository
import com.ks.culinario.data.entity.RecipeEntity
import com.ks.culinario.data.mapper.RecipeMapper
import org.springframework.stereotype.Repository

@Repository
class RecipeRepositoryImpl(
    private val mapper: RecipeMapper,
    private val shoppingListRepository: ShoppingListRepository
) : RecipeRepository {

    override fun findAll(): List<Recipe> {
        return   listOf(Recipe(1, "", "", Recipe.RecipeStep("",""), "", "", "", 1))
    }

    override fun findById(id: Int): Recipe? {
     return   Recipe(1, "", "", Recipe.RecipeStep("",""), "", "", "", 1)
    }

    override fun save(recipe: Recipe) {

    }

    override fun deleteById(id: Int) {
    }
}
