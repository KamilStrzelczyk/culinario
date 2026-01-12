package com.ks.culinario.domain.model

data class Recipe(
    val id: Int,
    val title: String,
    val description: String,
    val step: RecipeStep,
    val owner: String,
    val category: String,
    val created: String,
    val shoppingList: ShoppingList,
){
    data class RecipeStep(
        val title : String,
        val description: String,
    )
}
