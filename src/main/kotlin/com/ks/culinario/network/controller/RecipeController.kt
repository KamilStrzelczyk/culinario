package com.ks.culinario.network.controller

import com.ks.culinario.domain.service.RecipeService
import com.ks.culinario.network.dto.RecipeDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/recipes")
class RecipeController(private val recipeService: RecipeService) {

    @GetMapping("/all")
    fun getAll(): List<RecipeDTO> {
        return recipeService.getAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): RecipeDTO {
        return recipeService.get(id)
    }

    @PostMapping
    fun createRecipe(@RequestBody recipeDTO: RecipeDTO) {
         recipeService.create(recipeDTO)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        recipeService.delete(id)
    }
}
