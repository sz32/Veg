package com.ai.agent.productapis.repository

import com.ai.agent.productapis.models.Product
import com.ai.agent.productapis.models.ProductCreateRequest
import com.ai.agent.productapis.models.ProductUpdateRequest
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.atomic.AtomicInteger

class ProductRepository {
    private val products = ConcurrentHashMap<Int, Product>()
    private val idCounter = AtomicInteger(0)

    init {
        // Initialize with sample data
        addSampleProducts()
    }

    private fun addSampleProducts() {
        val sampleProducts = listOf(
            Product(
                id = nextId(),
                name = "Wireless Headphones",
                imageUrl = "https://picsum.photos/seed/product1/400/400",
                rating = 4.5f,
                price = 99.99,
                description = "Premium wireless headphones with active noise cancellation and 30-hour battery life.",
                category = "Electronics",
                stock = 50
            ),
            Product(
                id = nextId(),
                name = "Smart Watch",
                imageUrl = "https://picsum.photos/seed/product2/400/400",
                rating = 4.8f,
                price = 199.99,
                description = "Feature-rich smartwatch with fitness tracking, heart rate monitoring, and mobile payments.",
                category = "Electronics",
                stock = 30
            ),
            Product(
                id = nextId(),
                name = "Laptop",
                imageUrl = "https://picsum.photos/seed/product3/400/400",
                rating = 4.7f,
                price = 899.99,
                description = "Powerful laptop with Intel i7 processor, 16GB RAM, and 512GB SSD for professionals.",
                category = "Computers",
                stock = 20
            ),
            Product(
                id = nextId(),
                name = "Smartphone",
                imageUrl = "https://picsum.photos/seed/product4/400/400",
                rating = 4.6f,
                price = 699.99,
                description = "Latest smartphone with 5G connectivity, triple camera system, and all-day battery.",
                category = "Electronics",
                stock = 40
            ),
            Product(
                id = nextId(),
                name = "Bluetooth Speaker",
                imageUrl = "https://picsum.photos/seed/product5/400/400",
                rating = 4.4f,
                price = 49.99,
                description = "Portable Bluetooth speaker with 360-degree sound and waterproof design.",
                category = "Audio",
                stock = 75
            ),
            Product(
                id = nextId(),
                name = "Gaming Mouse",
                imageUrl = "https://picsum.photos/seed/product6/400/400",
                rating = 4.7f,
                price = 79.99,
                description = "RGB gaming mouse with 16000 DPI, programmable buttons, and ergonomic design.",
                category = "Accessories",
                stock = 60
            ),
            Product(
                id = nextId(),
                name = "Mechanical Keyboard",
                imageUrl = "https://picsum.photos/seed/product7/400/400",
                rating = 4.8f,
                price = 129.99,
                description = "Premium mechanical keyboard with customizable RGB lighting and Cherry MX switches.",
                category = "Accessories",
                stock = 35
            ),
            Product(
                id = nextId(),
                name = "4K Monitor",
                imageUrl = "https://picsum.photos/seed/product8/400/400",
                rating = 4.6f,
                price = 349.99,
                description = "27-inch 4K UHD monitor with HDR support and USB-C connectivity.",
                category = "Displays",
                stock = 25
            ),
            Product(
                id = nextId(),
                name = "Webcam",
                imageUrl = "https://picsum.photos/seed/product9/400/400",
                rating = 4.3f,
                price = 89.99,
                description = "1080p HD webcam with auto-focus and built-in microphone for video calls.",
                category = "Accessories",
                stock = 45
            ),
            Product(
                id = nextId(),
                name = "External SSD",
                imageUrl = "https://picsum.photos/seed/product10/400/400",
                rating = 4.9f,
                price = 149.99,
                description = "1TB portable SSD with USB 3.2 Gen 2 for ultra-fast data transfer.",
                category = "Storage",
                stock = 55
            )
        )

        sampleProducts.forEach { product ->
            products[product.id] = product
        }
    }

    private fun nextId(): Int = idCounter.incrementAndGet()

    fun getAllProducts(): List<Product> = products.values.toList()

    fun getProductById(id: Int): Product? = products[id]

    fun getProductsByCategory(category: String): List<Product> =
        products.values.filter { it.category.equals(category, ignoreCase = true) }

    fun searchProducts(query: String): List<Product> =
        products.values.filter {
            it.name.contains(query, ignoreCase = true) ||
                    it.description.contains(query, ignoreCase = true) ||
                    it.category.contains(query, ignoreCase = true)
        }

    fun getProductsPaginated(page: Int, pageSize: Int): Pair<List<Product>, Int> {
        val allProducts = getAllProducts()
        val totalItems = allProducts.size
        val startIndex = (page - 1) * pageSize
        val endIndex = minOf(startIndex + pageSize, totalItems)

        return if (startIndex < totalItems) {
            allProducts.subList(startIndex, endIndex) to totalItems
        } else {
            emptyList<Product>() to totalItems
        }
    }

    fun createProduct(request: ProductCreateRequest): Product {
        val newProduct = Product(
            id = nextId(),
            name = request.name,
            imageUrl = request.imageUrl,
            rating = request.rating,
            price = request.price,
            description = request.description,
            category = request.category,
            stock = request.stock
        )
        products[newProduct.id] = newProduct
        return newProduct
    }

    fun updateProduct(id: Int, request: ProductUpdateRequest): Product? {
        val existingProduct = products[id] ?: return null

        val updatedProduct = existingProduct.copy(
            name = request.name ?: existingProduct.name,
            imageUrl = request.imageUrl ?: existingProduct.imageUrl,
            rating = request.rating ?: existingProduct.rating,
            price = request.price ?: existingProduct.price,
            description = request.description ?: existingProduct.description,
            category = request.category ?: existingProduct.category,
            stock = request.stock ?: existingProduct.stock
        )

        products[id] = updatedProduct
        return updatedProduct
    }

    fun deleteProduct(id: Int): Boolean {
        return products.remove(id) != null
    }

    fun getCategories(): List<String> =
        products.values.map { it.category }.distinct().sorted()

    fun getTotalCount(): Int = products.size
}

