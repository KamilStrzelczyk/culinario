package com.ks.culinario.data.entity

import jakarta.persistence.*

@Entity
@Table(name = "shopping_lists")
data class ShoppingListEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    val title: String = "",
    val description: String = "",

    @Column(length = 4096)
    val itemsJson: String = ""
)
