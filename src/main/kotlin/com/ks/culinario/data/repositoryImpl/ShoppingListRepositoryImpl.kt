package com.ks.culinario.data.repositoryImpl

import com.ks.culinario.data.dao.ShoppingListDao
import com.ks.culinario.data.mapper.ShoppingListMapper
import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.domain.repository.ShoppingListRepository
import org.springframework.stereotype.Repository

@Repository
class ShoppingListRepositoryImpl(
    private val shoppingListDao: ShoppingListDao,
    private val mapper: ShoppingListMapper
) : ShoppingListRepository {

    override fun findAll(): List<ShoppingList> {
        return shoppingListDao.findAll().map { mapper.toDomain(it) }
    }

    override fun findById(id: Int): ShoppingList? {
        val entity = shoppingListDao.findById(id).orElse(null)
        return entity?.let { mapper.toDomain(it) }
    }

    override fun save(shoppingList: ShoppingList) {
        val entity = mapper.toEntity(shoppingList)
        shoppingListDao.save(entity)
    }

    override fun deleteById(id: Int) {
        shoppingListDao.deleteById(id)
    }
}
