package com.ks.culinario.domain.service

import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody

interface RecipeService {

    fun getAll(): List<String>

    fun get(id : String): String

    fun getByCategories(): List<String>

    fun createRecipe()
}
