package com.ai.agent.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.ai.agent.data.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {
    @Query("SELECT * FROM cart")
    fun getAllCartItems(): Flow<List<Cart>>

    @Query("SELECT productId FROM cart")
    fun getCartProductIds(): Flow<List<Int>>

    @Query("SELECT EXISTS(SELECT 1 FROM cart WHERE productId = :productId)")
    suspend fun isProductInCart(productId: Int): Boolean

    @Insert
    suspend fun addToCart(cart: Cart)

    @Query("DELETE FROM cart WHERE productId = :productId")
    suspend fun removeFromCart(productId: Int)

    @Query("DELETE FROM cart")
    suspend fun clearCart()

    @Query("SELECT COUNT(*) FROM cart")
    fun getCartItemCount(): Flow<Int>
}

