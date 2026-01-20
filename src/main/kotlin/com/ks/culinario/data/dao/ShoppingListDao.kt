package com.ks.culinario.data.dao

import com.ks.culinario.data.entity.ShoppingListEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ShoppingListDao : JpaRepository<ShoppingListEntity, Int>
