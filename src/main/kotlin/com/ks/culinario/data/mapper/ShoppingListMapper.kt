package com.ks.culinario.data.mapper

import com.ks.culinario.data.entity.ShoppingListEntity
import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.network.dto.NewShoppingListDTO
import com.ks.culinario.network.dto.ShoppingListDTO
import com.ks.culinario.network.dto.ShoppingListItemDTO
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.springframework.stereotype.Component

@Component
class ShoppingListMapper {

    private val json = Json { ignoreUnknownKeys = true }

    fun toDomain(entity: ShoppingListEntity): ShoppingList {
        val items: List<ShoppingList.ShoppingListItem> = if (entity.items.isBlank()) {
            emptyList()
        } else {
            try {
                json.decodeFromString(entity.items)
            } catch (e: Exception) {
                emptyList()
            }
        }

        return ShoppingList(
            id = entity.id ?: 0,
            title = entity.title,
            description = entity.description,
            items = items
        )
    }

    fun toDomain(dto: ShoppingListDTO): ShoppingList {
        return ShoppingList(
            id = dto.id,
            title = dto.title,
            description = dto.description,
            items = dto.items.map { it.toDomain() }
        )
    }

    fun toDomain(dto: NewShoppingListDTO): ShoppingList {
        return ShoppingList(
            id = 0,
            title = dto.title,
            description = dto.description,
            items = dto.items.map { it.toDomain() }
        )
    }

    fun toEntity(domain: ShoppingList): ShoppingListEntity {
        val itemsJson = try {
            json.encodeToString(domain.items)
        } catch (e: Exception) {
            ""
        }

        return ShoppingListEntity(
            id = if (domain.id == 0) null else domain.id,
            title = domain.title,
            description = domain.description,
            items = itemsJson
        )
    }

    fun toDTO(domain: ShoppingList): ShoppingListDTO {
        return ShoppingListDTO(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            items = domain.items.map { it.toDTO() }
        )
    }

    private fun ShoppingList.ShoppingListItem.toDTO(): ShoppingListItemDTO {
        return ShoppingListItemDTO(
            name = this.name,
            amount = this.amount
        )
    }

    private fun ShoppingListItemDTO.toDomain(): ShoppingList.ShoppingListItem {
        return ShoppingList.ShoppingListItem(
            name = this.name,
            amount = this.amount
        )
    }
}
