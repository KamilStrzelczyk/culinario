package com.ks.culinario.data.entity

data class ShoppingListEntity(
    val id: Int? = null,
    val title: String,
    val description: String,
    val itemsJson: String
)
