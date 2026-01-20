package com.ks.culinario.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "recipes")
data class RecipeEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val title: String = "",
    val description: String = "",

    val stepTitle: String = "",
    val stepDescription: String = "",

    val owner: String = "",
    val category: String = "",
    val created: String = "",

    val shoppingListId: Int? = null
)
