package com.ks.culinario.infrastructure.service

import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.domain.repository.RecipeRepository
import com.ks.culinario.domain.service.RecipeService
import org.springframework.stereotype.Service

@Service
class RecipeServiceImpl(
    private val recipeRepository: RecipeRepository
) : RecipeService {

    override fun getAll(): List<Recipe> {
        return recipeRepository.findAll()
    }

    override fun get(id: Int): Recipe {
        return recipeRepository.findById(id) 
            ?: throw RuntimeException("Recipe not found")
    }

    override fun create(recipe: Recipe): Recipe {
        return recipeRepository.save(recipe)
    }

    override fun delete(id: Int) {
        recipeRepository.deleteById(id)
    }
}
