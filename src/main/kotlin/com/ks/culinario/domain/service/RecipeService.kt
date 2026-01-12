package com.ks.culinario.domain.service

import com.ks.culinario.domain.model.Recipe

interface RecipeService {
    fun getAll(): List<Recipe>
    fun get(id: Int): Recipe
    fun create(recipe: Recipe): Recipe
    fun delete(id: Int)
}
