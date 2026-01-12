package com.ks.culinario.infrastructure.repositoryImpl

import com.ks.culinario.infrastructure.repository.RecipeRepository
import org.springframework.stereotype.Repository

@Repository
class RecipeRepositoryImpl: RecipeRepository {
    override fun getAll(): List<String> {
        TODO("Not yet implemented")
    }

    override fun get(id: String): String {
        TODO("Not yet implemented")
    }

    override fun getByCategories(): List<String> {
        TODO("Not yet implemented")
    }

    override fun createRecipe() {
        TODO("Not yet implemented")
    }
}