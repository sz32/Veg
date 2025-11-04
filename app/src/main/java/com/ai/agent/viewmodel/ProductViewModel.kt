package com.ai.agent.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.ai.agent.data.DummyProductData
import com.ai.agent.data.ProductWithCartStatus
import com.ai.agent.data.database.AppDatabase
import com.ai.agent.data.repository.ProductRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch


data class ProductUiState(
    val products: List<ProductWithCartStatus> = emptyList(),
    val isLoading: Boolean = false,
    val cartItemCount: Int = 0
)

class ProductViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: ProductRepository

    private val _uiState = MutableStateFlow(ProductUiState())
    val uiState: StateFlow<ProductUiState> = _uiState.asStateFlow()

    init {
        val database = AppDatabase.getDatabase(application)
        repository = ProductRepository(
            productDao = database.productDao(),
            cartDao = database.cartDao()
        )

        loadProducts()
        observeCartItemCount()
    }

    private fun loadProducts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }

            // Initialize database with dummy data if empty
            val productCount = repository.getProductCount()
            if (productCount == 0) {
                repository.insertProducts(DummyProductData.getProducts())
            }

            // Observe products with cart status
            repository.getAllProductsWithCartStatus().collect { productsWithStatus ->
                _uiState.update {
                    it.copy(
                        products = productsWithStatus,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun observeCartItemCount() {
        viewModelScope.launch {
            repository.getCartItemCount().collect { count ->
                _uiState.update { it.copy(cartItemCount = count) }
            }
        }
    }

    fun addToCart(productId: Int) {
        viewModelScope.launch {
            repository.addToCart(productId)
        }
    }

    fun removeFromCart(productId: Int) {
        viewModelScope.launch {
            repository.removeFromCart(productId)
        }
    }

    fun clearCart() {
        viewModelScope.launch {
            repository.clearCart()
        }
    }
}
