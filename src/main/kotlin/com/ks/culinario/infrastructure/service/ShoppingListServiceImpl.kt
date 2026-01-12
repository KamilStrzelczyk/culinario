package com.ks.culinario.infrastructure.service

import com.ks.culinario.data.mapper.ShoppingListMapper
import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.domain.repository.ShoppingListRepository
import com.ks.culinario.domain.service.ShoppingListService
import com.ks.culinario.network.dto.ShoppingListDTO
import org.springframework.stereotype.Service

@Service
class ShoppingListServiceImpl(
    private val shoppingListRepository: ShoppingListRepository,
    private val shoppingListMapper: ShoppingListMapper
) : ShoppingListService {

    override fun getAll(): List<ShoppingListDTO> {
        return shoppingListRepository.findAll().map { shoppingListMapper.toDTO(it) }
    }

    override fun get(id: Int): ShoppingListDTO =shoppingListRepository.findById(id)?.let {
        shoppingListMapper.toDTO(it)
    } ?: throw RuntimeException("Shopping list not found")

    override fun create(shoppingList: ShoppingListDTO) {
         shoppingListRepository.save(shoppingListMapper.toDomain(shoppingList))
    }

    override fun delete(id: Int) {
        shoppingListRepository.deleteById(id)
    }
}
