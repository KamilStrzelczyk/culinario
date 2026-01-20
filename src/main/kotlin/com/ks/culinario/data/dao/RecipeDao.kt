package com.ks.culinario.data.dao

import com.ks.culinario.data.entity.RecipeEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RecipeDao : JpaRepository<RecipeEntity, Int>
