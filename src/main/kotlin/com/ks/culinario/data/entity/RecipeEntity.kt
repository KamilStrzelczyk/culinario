package com.ks.culinario.data.entity

data class RecipeEntity(
    val id: Int? = null,
    val title: String,
    val description: String,
    val stepTitle: String,
    val stepDescription: String,
    val owner: String,
    val category: String,
    val created: String,
    val shoppingListId: Int?
)
