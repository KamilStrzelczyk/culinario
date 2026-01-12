package com.ks.culinario.web.controller

import com.ks.culinario.domain.service.ShoppingListService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shoppingList")
class ShoppingListController(private val shoppingListService: ShoppingListService) {

    @GetMapping("/all")
    fun getAll(): List<String> = emptyList()

    @GetMapping("{id}/active")
    fun getActive(@PathVariable id: String): List<String> = emptyList()

    @PostMapping
    fun create(@RequestBody shoppingList: String) = shoppingListService.create(shoppingList)
}

data class ShoppingList(
    val id: Int,
    val title: String,
    val description: String,
    val items: List<ShoppingListItem>
) {
    data class ShoppingListItem(val name: String, val amount: Int)
}