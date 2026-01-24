package com.ks.culinario.infrastructure.service

import com.ks.culinario.data.mapper.RecipeMapper
import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.domain.repository.RecipeRepository
import com.ks.culinario.network.dto.NewRecipeDTO
import com.ks.culinario.network.dto.RecipeDTO
import com.ks.culinario.network.dto.RecipeStepDTO
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class RecipeServiceImplTest {

    private val recipeRepository: RecipeRepository = mockk()
    private val recipeMapper: RecipeMapper = mockk()
    private val recipeService = RecipeServiceImpl(recipeRepository, recipeMapper)

    private val sampleRecipe = Recipe(1, "Test", "Desc", Recipe.RecipeStep("S1", "D1"), "Own", "Cat", "Date", 1)
    private val sampleRecipeDTO = RecipeDTO(1, "Test", "Desc", RecipeStepDTO("S1", "D1"), "Own", "Cat", "Date", 1)

    @Test
    fun `should return RecipeDTO when recipe exists`() {
        // GIVEN
        every { recipeRepository.findById(1) } returns sampleRecipe
        every { recipeMapper.toDTO(sampleRecipe) } returns sampleRecipeDTO

        // WHEN
        val result = recipeService.get(1)

        // THEN
        assertEquals(sampleRecipeDTO, result)
    }

    @Test
    fun `should throw exception when recipe does not exist`() {
        // GIVEN
        every { recipeRepository.findById(1) } returns null

        // WHEN & THEN
        assertThrows<RuntimeException> {
            recipeService.get(1)
        }
    }

    @Test
    fun `should return all recipes`() {
        // GIVEN
        every { recipeRepository.findAll() } returns listOf(sampleRecipe)
        every { recipeMapper.toDTO(sampleRecipe) } returns sampleRecipeDTO

        // WHEN
        val result = recipeService.getAll()

        // THEN
        assertEquals(1, result.size)
        assertEquals(sampleRecipeDTO, result[0])
        verify(exactly = 1) { recipeRepository.findAll() }
    }

    @Test
    fun `should create recipe and return its DTO`() {
        // GIVEN
        val newRecipeDTO = NewRecipeDTO("Test", "Desc", RecipeStepDTO("S1", "D1"), "Own", "Cat", 1)
        val newDomain = sampleRecipe.copy(id = 0)
        
        every { recipeMapper.toDomain(newRecipeDTO) } returns newDomain
        every { recipeRepository.save(newDomain) } just Runs
        every { recipeMapper.toDTO(sampleRecipe) } returns sampleRecipeDTO

        // WHEN
        recipeService.create(newRecipeDTO)

        // THEN
        verify(exactly = 1) { recipeRepository.save(newDomain) }
    }

    @Test
    fun `should delete recipe`() {
        // GIVEN
        every { recipeRepository.deleteById(1) } just runs

        // WHEN
        recipeService.delete(1)

        // THEN
        verify(exactly = 1) { recipeRepository.deleteById(1) }
    }
}
