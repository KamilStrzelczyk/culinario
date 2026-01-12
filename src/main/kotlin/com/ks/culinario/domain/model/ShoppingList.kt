package com.ks.culinario.domain.model

data class ShoppingList(
    val id: Int,
    val title: String,
    val description: String,
    val items: List<ShoppingListItem>
) {
    data class ShoppingListItem(val name: String, val amount: Int)
}
