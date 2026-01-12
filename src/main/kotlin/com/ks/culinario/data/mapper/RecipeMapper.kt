package com.ks.culinario.data.mapper

import com.ks.culinario.data.entity.RecipeEntity
import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.network.dto.RecipeDTO
import com.ks.culinario.network.dto.RecipeStepDTO
import org.springframework.stereotype.Component

@Component
class RecipeMapper(
    private val shoppingListMapper: ShoppingListMapper
) {

    fun toDomain(recipe: RecipeEntity): Recipe {
        return Recipe(
            id = recipe.id ?: 0,
            title = recipe.title,
            description = recipe.description,
            step = Recipe.RecipeStep(recipe.stepTitle, recipe.stepDescription),
            owner = recipe.owner,
            category = recipe.category,
            created = recipe.created,
            shoppingListId = recipe.shoppingListId,
            )
    }

    fun toDomain(recipe: RecipeDTO): Recipe {
        return Recipe(
            id = recipe.id ?: 0,
            title = recipe.title,
            description = recipe.description,
            step = Recipe.RecipeStep(recipe.step.title, recipe.step.description),
            owner = recipe.owner,
            category = recipe.category,
            created = recipe.created,
            shoppingListId = recipe.shoppingListId,
        )
    }

    fun toEntity(domain: Recipe): RecipeEntity {
        return RecipeEntity(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            stepTitle = domain.step.title,
            stepDescription = domain.step.description,
            owner = domain.owner,
            category = domain.category,
            created = domain.created,
            shoppingListId = domain.shoppingListId
        )
    }

    fun toDTO(domain: Recipe): RecipeDTO {
        return RecipeDTO(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            step = domain.step.toDTO(),
            owner = domain.owner,
            category = domain.category,
            created = domain.created,
            shoppingListId = domain.shoppingListId
        )
    }

    private fun Recipe.RecipeStep.toDTO(): RecipeStepDTO {
        return RecipeStepDTO(
            title = this.title,
            description = this.description
        )
    }
}
