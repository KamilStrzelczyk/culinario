package com.ks.culinario.data.mapper

import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.data.entity.RecipeEntity
import org.springframework.stereotype.Component

@Component
class RecipeMapper(
    private val shoppingListMapper: ShoppingListMapper
) {

    fun toDomain(entity: RecipeEntity, shoppingList: ShoppingList?): Recipe {
        return Recipe(
            id = entity.id ?: 0,
            title = entity.title,
            description = entity.description,
            step = Recipe.RecipeStep(entity.stepTitle, entity.stepDescription),
            owner = entity.owner,
            category = entity.category,
            created = entity.created,
            shoppingList = shoppingList ?: ShoppingList(0, "", "", emptyList())
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
            shoppingListId = domain.shoppingList.id
        )
    }
}
