package com.ks.culinario.data.mapper

import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.data.entity.ShoppingListEntity
import org.springframework.stereotype.Component

@Component
class ShoppingListMapper {

    fun toDomain(entity: ShoppingListEntity): ShoppingList {
        return ShoppingList(
            id = entity.id ?: 0,
            title = entity.title,
            description = entity.description,
            items = emptyList()
        )
    }

    fun toEntity(domain: ShoppingList): ShoppingListEntity {
        return ShoppingListEntity(
            id = domain.id,
            title = domain.title,
            description = domain.description,
            itemsJson = ""
        )
    }
}
