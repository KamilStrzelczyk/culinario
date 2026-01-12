package com.ks.culinario.network.controller

import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.domain.service.ShoppingListService
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shoppingList")
class ShoppingListController(private val shoppingListService: ShoppingListService) {

    @GetMapping("/all")
    fun getAll(): List<ShoppingList> = shoppingListService.getAll()

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ShoppingList = shoppingListService.get(id)

    @PostMapping
    fun create(@RequestBody shoppingList: ShoppingList) = shoppingListService.create(shoppingList)
    
    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) = shoppingListService.delete(id)
}
