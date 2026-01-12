package com.ks.culinario.data.repositoryImpl

import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.domain.repository.ShoppingListRepository
import com.ks.culinario.data.entity.ShoppingListEntity
import com.ks.culinario.data.mapper.ShoppingListMapper
import org.springframework.stereotype.Repository

@Repository
class ShoppingListRepositoryImpl(
    private val mapper: ShoppingListMapper
) : ShoppingListRepository {

    private val database = mutableMapOf<Int, ShoppingListEntity>()
    private var idCounter = 1

    override fun findAll(): List<ShoppingList> {
        return database.values.map { mapper.toDomain(it) }
    }

    override fun findById(id: Int): ShoppingList? {
        return database[id]?.let { mapper.toDomain(it) }
    }

    override fun save(shoppingList: ShoppingList): ShoppingList {
        val entity = mapper.toEntity(shoppingList)
        val id = entity.id ?: idCounter++
        val savedEntity = entity.copy(id = id)
        database[id] = savedEntity
        return mapper.toDomain(savedEntity)
    }

    override fun deleteById(id: Int) {
        database.remove(id)
    }
}
