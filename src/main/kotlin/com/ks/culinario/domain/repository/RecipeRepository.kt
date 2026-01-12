package com.ks.culinario.domain.repository

import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.network.dto.RecipeDTO

interface RecipeRepository {
    fun findAll(): List<Recipe>
    fun findById(id: Int): Recipe?
    fun save(recipe: Recipe)
    fun deleteById(id: Int)
}
