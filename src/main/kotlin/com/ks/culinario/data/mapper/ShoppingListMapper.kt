package com.ks.culinario.data.mapper

import com.ks.culinario.data.entity.ShoppingListEntity
import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.network.dto.ShoppingListDTO
import com.ks.culinario.network.dto.ShoppingListItemDTO
import org.springframework.stereotype.Component

@Component
class ShoppingListMapper {

    fun toDomain(shoppingList: ShoppingListEntity): ShoppingList {
        return ShoppingList(
            id = shoppingList.id ?: 0,
            title = shoppingList.title,
            description = shoppingList.description,
            items = emptyList()
        )
    }

    fun toDomain(shoppingList: ShoppingListDTO): ShoppingList {
        return ShoppingList(
            id = shoppingList.id ?: 0,
            title = shoppingList.title,
            description = shoppingList.description,
            items = emptyList()
        )
    }

    fun toEntity(shoppingList: ShoppingList): ShoppingListEntity {
        return ShoppingListEntity(
            id = shoppingList.id,
            title = shoppingList.title,
            description = shoppingList.description,
            itemsJson = ""
        )
    }

    fun toDTO(shoppingList: ShoppingList): ShoppingListDTO {
        return ShoppingListDTO(
            id = shoppingList.id,
            title = shoppingList.title,
            description = shoppingList.description,
            items = shoppingList.items.map { it.toDTO() }
        )
    }

    private fun ShoppingList.ShoppingListItem.toDTO(): ShoppingListItemDTO {
        return ShoppingListItemDTO(
            name = this.name,
            amount = this.amount
        )
    }
}
