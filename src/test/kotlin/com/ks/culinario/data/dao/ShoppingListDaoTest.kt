package com.ks.culinario.data.dao

import com.ks.culinario.data.entity.ShoppingListEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager

@DataJpaTest
class ShoppingListDaoTest {

    @Autowired
    private lateinit var entityManager: TestEntityManager

    @Autowired
    private lateinit var shoppingListDao: ShoppingListDao

    @Test
    fun `should save and find shopping list by id`() {
        // GIVEN
        val list = ShoppingListEntity(title = "My List")
        entityManager.persistAndFlush(list)

        // WHEN
        val foundList = shoppingListDao.findById(list.id!!)

        // THEN
        assertThat(foundList).isPresent
        assertThat(foundList.get().title).isEqualTo("My List")
    }
}
