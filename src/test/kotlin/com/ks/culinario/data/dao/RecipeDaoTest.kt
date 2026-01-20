package com.ks.culinario.data.dao

import com.ks.culinario.data.entity.RecipeEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

@DataJpaTest
class RecipeDaoTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var recipeDao: RecipeDao

    @Test
    fun `should save and find recipe by id`() {
        // GIVEN
        val recipe = RecipeEntity(title = "Test Recipe")
        entityManager.persistAndFlush(recipe)

        // WHEN
        val foundRecipe = recipeDao.findById(recipe.id!!)

        // THEN
        assertThat(foundRecipe).isPresent
        assertThat(foundRecipe.get().title).isEqualTo("Test Recipe")
    }

    @Test
    fun `should return all recipes`() {
        // GIVEN
        entityManager.persist(RecipeEntity(title = "Recipe 1"))
        entityManager.persist(RecipeEntity(title = "Recipe 2"))
        entityManager.flush()

        // WHEN
        val recipes = recipeDao.findAll()

        // THEN
        assertThat(recipes).hasSize(2)
    }
}
