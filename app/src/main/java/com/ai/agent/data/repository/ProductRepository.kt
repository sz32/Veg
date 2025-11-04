package com.ai.agent.data.repository

import com.ai.agent.data.Cart
import com.ai.agent.data.Product
import com.ai.agent.data.ProductWithCartStatus
import com.ai.agent.data.dao.CartDao
import com.ai.agent.data.dao.ProductDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class ProductRepository(
    private val productDao: ProductDao,
    private val cartDao: CartDao
) {
    // Get all products with their cart status
    fun getAllProductsWithCartStatus(): Flow<List<ProductWithCartStatus>> {
        return combine(
            productDao.getAllProducts(),
            cartDao.getCartProductIds()
        ) { products, cartProductIds ->
            products.map { product ->
                ProductWithCartStatus(
                    product = product,
                    isInCart = cartProductIds.contains(product.id)
                )
            }
        }
    }

    suspend fun insertProducts(products: List<Product>) {
        productDao.insertAllProducts(products)
    }

    suspend fun getProductCount(): Int {
        return productDao.getProductCount()
    }

    suspend fun addToCart(productId: Int) {
        if (!cartDao.isProductInCart(productId)) {
            cartDao.addToCart(Cart(productId = productId))
        }
    }

    suspend fun removeFromCart(productId: Int) {
        cartDao.removeFromCart(productId)
    }

    fun getCartItemCount(): Flow<Int> {
        return cartDao.getCartItemCount()
    }

    suspend fun clearCart() {
        cartDao.clearCart()
    }
}

