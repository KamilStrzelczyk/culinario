package com.ks.culinario.data.repositoryImpl

import com.ks.culinario.data.dao.RecipeDao
import com.ks.culinario.data.mapper.RecipeMapper
import com.ks.culinario.domain.model.Recipe
import com.ks.culinario.domain.repository.RecipeRepository
import org.springframework.stereotype.Repository

@Repository
class RecipeRepositoryImpl(
    private val recipeDao: RecipeDao,
    private val recipeMapper: RecipeMapper
) : RecipeRepository {

    override fun findAll(): List<Recipe> {
        return recipeDao.findAll().map { recipeMapper.toDomain(it) }
    }

    override fun findById(id: Int): Recipe? {
        val entity = recipeDao.findById(id).orElse(null) ?: return null
        return recipeMapper.toDomain(entity)
    }

    override fun save(recipe: Recipe) {
        val entity = recipeMapper.toEntity(recipe)
        recipeDao.save(entity)
    }

    override fun deleteById(id: Int) {
        recipeDao.deleteById(id)
    }
}
