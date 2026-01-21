package com.ks.culinario.network.controller

import com.ks.culinario.domain.service.ShoppingListService
import com.ks.culinario.network.dto.NewShoppingListDTO
import com.ks.culinario.network.dto.ShoppingListDTO
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

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
    fun create(@RequestBody newShoppingListDTO: NewShoppingListDTO) {
         shoppingListService.create(newShoppingListDTO)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        shoppingListService.delete(id)
    }
}
