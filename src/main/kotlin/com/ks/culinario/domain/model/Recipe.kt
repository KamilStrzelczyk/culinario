package com.ks.culinario.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val step: RecipeStep,
    val owner: String,
    val category: String,
    val created: String,
    val shoppingListId: Int?,
) {
    data class RecipeStep(
        val title: String,
        val description: String,
    )
}
