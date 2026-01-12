package com.ks.culinario.network.controller

import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.domain.service.RecipeService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes")
class RecipeController(private val recipeService : RecipeService) {

    @GetMapping("/all")
    fun getAll(): List<Recipe> = recipeService.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): Recipe = recipeService.get(id)

    @PostMapping
    fun createRecipe(@RequestBody recipe: Recipe) = recipeService.create(recipe)
    
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) = recipeService.delete(id)
}
