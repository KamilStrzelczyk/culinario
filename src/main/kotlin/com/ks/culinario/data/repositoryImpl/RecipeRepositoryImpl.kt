package com.ks.culinario.data.repositoryImpl

import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.domain.repository.RecipeRepository
import com.ks.culinario.domain.repository.ShoppingListRepository
import com.ks.culinario.data.entity.RecipeEntity
import com.ks.culinario.data.mapper.RecipeMapper
import org.springframework.stereotype.Repository

@Repository
class RecipeRepositoryImpl(
    private val mapper: RecipeMapper,
    private val shoppingListRepository: ShoppingListRepository
) : RecipeRepository {

    private val database = mutableMapOf<Int, RecipeEntity>()
    private var idCounter = 1

    override fun findAll(): List<Recipe> {
        return database.values.map { entity ->
            val shoppingList = entity.shoppingListId?.let { shoppingListRepository.findById(it) }
            mapper.toDomain(entity, shoppingList)
        }
    }

    override fun findById(id: Int): Recipe? {
        val entity = database[id] ?: return null
        val shoppingList = entity.shoppingListId?.let { shoppingListRepository.findById(it) }
        return mapper.toDomain(entity, shoppingList)
    }

    override fun save(recipe: Recipe): Recipe {
        val entity = mapper.toEntity(recipe)
        val id = entity.id ?: idCounter++
        val savedEntity = entity.copy(id = id)
        database[id] = savedEntity
        
        val shoppingList = savedEntity.shoppingListId?.let { shoppingListRepository.findById(it) }
        return mapper.toDomain(savedEntity, shoppingList)
    }

    override fun deleteById(id: Int) {
        database.remove(id)
    }
}
