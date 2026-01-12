package com.ks.culinario.infrastructure.repositoryImpl

import com.ks.culinario.infrastructure.repository.ShoppingListRepository
import org.springframework.stereotype.Repository


@Repository
class ShoppingListRepositoryImpl: ShoppingListRepository {
    override fun getAll(): List<String> {
        TODO("Not yet implemented")
    }

    override fun getActive(): List<String> {
        TODO("Not yet implemented")
    }

    override fun create(shoppingList: String) {
        TODO("Not yet implemented")
    }
}