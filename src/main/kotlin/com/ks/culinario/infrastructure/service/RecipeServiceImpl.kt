package com.ks.culinario.infrastructure.service

import com.ks.culinario.data.mapper.RecipeMapper
import com.ks.culinario.domain.exception.ResourceNotFoundException
import com.ks.culinario.domain.repository.RecipeRepository
import com.ks.culinario.domain.service.RecipeService
import com.ks.culinario.network.dto.RecipeDTO
import org.springframework.stereotype.Service

@Service
class RecipeServiceImpl(
    private val recipeRepository: RecipeRepository,
    private val recipeMapper: RecipeMapper
) : RecipeService {

    override fun getAll(): List<RecipeDTO> = recipeRepository.findAll().map { recipeMapper.toDTO(it) }

    override fun get(id: Int): RecipeDTO =
        recipeRepository.findById(id)?.let { recipeMapper.toDTO(it) }
            ?: throw ResourceNotFoundException("Recipe not found with id: $id")

    override fun create(recipeDTO: RecipeDTO) {
        val domain = recipeMapper.toDomain(recipeDTO)
         recipeRepository.save(domain)
    }

    override fun delete(id: Int) {
        recipeRepository.deleteById(id)
    }
}
