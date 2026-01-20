package com.ks.culinario.domain.repository

import com.ks.culinario.domain.model.Recipe

interface RecipeRepository {
    fun findAll(): List<Recipe>
    fun findById(id: Int): Recipe?
    fun save(recipe: Recipe)
    fun deleteById(id: Int)
}
