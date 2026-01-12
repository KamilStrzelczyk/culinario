package com.ks.culinario.network.controller

import com.ks.culinario.domain.service.ShoppingListService
import com.ks.culinario.network.dto.ShoppingListDTO
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/shoppingList")
class ShoppingListController(private val shoppingListService: ShoppingListService) {

    @GetMapping("/all")
    fun getAll(): List<ShoppingListDTO> {
        return shoppingListService.getAll()
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ShoppingListDTO {
        return shoppingListService.get(id)
    }

    @PostMapping
    fun create(@RequestBody shoppingListDTO: ShoppingListDTO)  {
         shoppingListService.create(shoppingListDTO)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        shoppingListService.delete(id)
    }
}
