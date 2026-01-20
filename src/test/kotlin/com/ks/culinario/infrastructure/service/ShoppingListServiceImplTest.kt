package com.ks.culinario.infrastructure.service

import com.ks.culinario.data.mapper.ShoppingListMapper
import com.ks.culinario.domain.model.ShoppingList
import com.ks.culinario.domain.repository.ShoppingListRepository
import com.ks.culinario.network.dto.ShoppingListDTO
import com.ks.culinario.network.dto.ShoppingListItemDTO
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ShoppingListServiceImplTest {

    private val shoppingListRepository: ShoppingListRepository = mockk()
    private val shoppingListMapper: ShoppingListMapper = mockk()
    private val shoppingListService = ShoppingListServiceImpl(shoppingListRepository, shoppingListMapper)

    private val sampleList = ShoppingList(1, "List", "Desc", listOf(ShoppingList.ShoppingListItem("Item", 1)))
    private val sampleListDTO = ShoppingListDTO(1, "List", "Desc", listOf(ShoppingListItemDTO("Item", 1)))

    @Test
    fun `should return ShoppingListDTO when shopping list exists`() {
        // GIVEN
        every { shoppingListRepository.findById(1) } returns sampleList
        every { shoppingListMapper.toDTO(sampleList) } returns sampleListDTO

        // WHEN
        val result = shoppingListService.get(1)

        // THEN
        assertEquals(sampleListDTO, result)
    }

    @Test
    fun `should throw exception when shopping list does not exist`() {
        // GIVEN
        every { shoppingListRepository.findById(1) } returns null

        // WHEN & THEN
        assertThrows<RuntimeException> {
            shoppingListService.get(1)
        }
    }

    @Test
    fun `should return all shopping lists`() {
        // GIVEN
        every { shoppingListRepository.findAll() } returns listOf(sampleList)
        every { shoppingListMapper.toDTO(sampleList) } returns sampleListDTO

        // WHEN
        val result = shoppingListService.getAll()

        // THEN
        assertEquals(1, result.size)
        assertEquals(sampleListDTO, result[0])
        verify(exactly = 1) { shoppingListRepository.findAll() }
    }

    @Test
    fun `should create shopping list and return its DTO`() {
        // GIVEN
        val newDTO = sampleListDTO.copy(id = 0)
        val newDomain = sampleList.copy(id = 0)

        every { shoppingListMapper.toDomain(newDTO) } returns newDomain
        every { shoppingListRepository.save(newDomain) } returns Unit
        every { shoppingListMapper.toDTO(sampleList) } returns sampleListDTO

        // WHEN
        shoppingListService.create(newDTO)

        // THEN
        verify(exactly = 1) { shoppingListRepository.save(newDomain) }
    }

    @Test
    fun `should delete shopping list`() {
        // GIVEN
        every { shoppingListRepository.deleteById(1) } just runs

        // WHEN
        shoppingListService.delete(1)

        // THEN
        verify(exactly = 1) { shoppingListRepository.deleteById(1) }
    }
}
