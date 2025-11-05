package com.ai.agent.productapis.repository

import com.ai.agent.productapis.models.Cart
import com.ai.agent.productapis.models.CartItem
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class CartRepository(private val productRepository: ProductRepository) {
    private val cartItems = ConcurrentHashMap<Int, CartItem>()
    private val idCounter = AtomicInteger(0)

    private fun nextId(): Int = idCounter.incrementAndGet()

    fun addToCart(productId: Int, quantity: Int): CartItem? {
        // Check if product exists
        val product = productRepository.getProductById(productId) ?: return null

        // Validate quantity
        if (quantity <= 0) return null
        if (quantity > product.stock) return null

        // Check if product already exists in cart
        val existingItem = cartItems.values.find { it.productId == productId }

        return if (existingItem != null) {
            // Update quantity
            val newQuantity = existingItem.quantity + quantity
            if (newQuantity > product.stock) return null

            val updatedItem = existingItem.copy(quantity = newQuantity)
            cartItems[existingItem.id] = updatedItem
            updatedItem
        } else {
            // Create new cart item
            val newItem = CartItem(
                id = nextId(),
                productId = productId,
                product = product,
                quantity = quantity
            )
            cartItems[newItem.id] = newItem
            newItem
        }
    }

    fun removeFromCart(cartItemId: Int): Boolean {
        return cartItems.remove(cartItemId) != null
    }

    fun updateCartItemQuantity(cartItemId: Int, quantity: Int): CartItem? {
        val cartItem = cartItems[cartItemId] ?: return null

        // Validate quantity
        if (quantity <= 0) return null
        if (quantity > cartItem.product.stock) return null

        val updatedItem = cartItem.copy(quantity = quantity)
        cartItems[cartItemId] = updatedItem
        return updatedItem
    }

    fun getCartItem(cartItemId: Int): CartItem? {
        return cartItems[cartItemId]
    }

    fun getAllCartItems(): List<CartItem> {
        return cartItems.values.toList()
    }

    fun getCart(): Cart {
        val items = getAllCartItems()
        val totalItems = items.sumOf { it.quantity }
        val totalPrice = items.sumOf { it.product.price * it.quantity }

        return Cart(
            items = items,
            totalItems = totalItems,
            totalPrice = totalPrice
        )
    }

    fun clearCart(): Boolean {
        cartItems.clear()
        return true
    }

    fun getCartItemByProductId(productId: Int): CartItem? {
        return cartItems.values.find { it.productId == productId }
    }

    fun getTotalItemsCount(): Int {
        return cartItems.values.sumOf { it.quantity }
    }

    fun getTotalPrice(): Double {
        return cartItems.values.sumOf { it.product.price * it.quantity }
    }
}

