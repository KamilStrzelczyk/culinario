package com.ks.culinario.network.dto

data class RecipeDTO(
    val id: Int,
    val title: String,
    val description: String,
    val step: RecipeStepDTO,
    val owner: String,
    val category: String,
    val created: String,
    val shoppingListId: Int?
)

data class RecipeStepDTO(
    val title: String,
    val description: String
)
