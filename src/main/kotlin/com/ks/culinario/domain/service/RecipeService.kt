package com.ks.culinario.domain.service

import com.ks.culinario.network.dto.RecipeDTO

interface RecipeService {
    fun getAll(): List<RecipeDTO>
    fun get(id: Int): RecipeDTO
    fun create(recipeDTO: RecipeDTO)
    fun delete(id: Int)
}
