package com.ks.culinario.domain.service

import com.ks.culinario.network.dto.NewRecipeDTO
import com.ks.culinario.network.dto.RecipeDTO

interface RecipeService {
    fun getAll(): List<RecipeDTO>
    fun get(id: Int): RecipeDTO
    fun create(newRecipeDTO: NewRecipeDTO)
    fun delete(id: Int)
}
