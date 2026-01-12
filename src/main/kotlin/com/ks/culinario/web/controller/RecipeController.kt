package com.ks.culinario.web.controller

import com.ks.culinario.domain.service.RecipeService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/recipes")
class RecipeController(private val recipeService : RecipeService) {

    @GetMapping("all")
    fun getAll(): List<String> = emptyList()

    @GetMapping("/{id}")
    fun get(@PathVariable id: String): String = ""

    @GetMapping("/{id}/items")
    fun getByCategories(@PathVariable id: String): List<String> = emptyList()

    @PostMapping
    fun createRecipe(@RequestBody recipe: String) = recipeService.createRecipe()
}

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