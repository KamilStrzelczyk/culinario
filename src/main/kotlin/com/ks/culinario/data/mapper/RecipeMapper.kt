package com.ks.culinario.data.mapper

import com.ks.culinario.data.entity.RecipeEntity
import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.network.dto.NewRecipeDTO
import com.ks.culinario.network.dto.RecipeDTO
import com.ks.culinario.network.dto.RecipeStepDTO
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class RecipeMapper {

    fun toDomain(entity: RecipeEntity): Recipe {
        return Recipe(
            id = entity.id ?: 0,
            title = entity.title,
            description = entity.description,
            step = Recipe.RecipeStep(entity.stepTitle, entity.stepDescription),
            owner = entity.owner,
            category = entity.category,
            created = entity.created,
            shoppingListId = entity.shoppingListId
        )
    }

    fun toDomain(dto: RecipeDTO): Recipe {
        return Recipe(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            step = dto.step.toDomain(),
            owner = dto.owner,
            category = dto.category,
            created = dto.created,
            shoppingListId = dto.shoppingListId
        )
    }

    fun toDomain(dto: NewRecipeDTO): Recipe {
        return Recipe(
            id = 0, // Nowy przepis, ID nada baza
            title = dto.title,
            description = dto.description,
            step = dto.step.toDomain(),
            owner = dto.owner,
            category = dto.category,
            created = LocalDate.now().toString(), // Ustawiamy datę utworzenia
            shoppingListId = dto.shoppingListId
        )
    }

    fun toEntity(domain: Recipe): RecipeEntity {
        return RecipeEntity(
            id = if (domain.id == 0) null else domain.id, // 0 w domenie -> null w encji (dla autoincrement)
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
            shoppingListId = domain.shoppingListId ?: 0
        )
    }

    private fun Recipe.RecipeStep.toDTO(): RecipeStepDTO {
        return RecipeStepDTO(
            title = this.title,
            description = this.description
        )
    }

    private fun RecipeStepDTO.toDomain(): Recipe.RecipeStep {
        return Recipe.RecipeStep(
            title = this.title,
            description = this.description
        )
    }
}
