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


    override fun findAll(): List<ShoppingList> {
        return listOf(ShoppingList(1, "", "", emptyList()))
    }

    override fun findById(id: Int): ShoppingList? {
        return ShoppingList(1, "", "", emptyList())
    }

    override fun save(shoppingList: ShoppingList) {

    }

    override fun deleteById(id: Int) {
    }
}
