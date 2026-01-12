package com.ks.culinario.infrastructure.repository

interface RecipeRepository {
    fun getAll(): List<String>

    fun get(id : String): String

    fun getByCategories(): List<String>

    fun createRecipe()
}