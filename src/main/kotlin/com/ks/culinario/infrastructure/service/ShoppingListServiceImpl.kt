package com.ks.culinario.infrastructure.service

import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.domain.repository.ShoppingListRepository
import com.ks.culinario.domain.service.ShoppingListService
import org.springframework.stereotype.Service

@Service
class ShoppingListServiceImpl(
    private val shoppingListRepository: ShoppingListRepository
) : ShoppingListService {

    override fun getAll(): List<ShoppingList> {
        return shoppingListRepository.findAll()
    }

    override fun get(id: Int): ShoppingList {
        return shoppingListRepository.findById(id) 
            ?: throw RuntimeException("Shopping list not found")
    }

    override fun create(shoppingList: ShoppingList): ShoppingList {
        return shoppingListRepository.save(shoppingList)
    }

    override fun delete(id: Int) {
        shoppingListRepository.deleteById(id)
    }
}
