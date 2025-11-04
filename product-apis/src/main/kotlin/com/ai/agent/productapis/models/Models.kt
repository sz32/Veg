package com.ai.agent.productapis.models

import kotlinx.serialization.Serializable

@Serializable
data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val rating: Float,
    val price: Double,
    val description: String = "",
    val category: String = "General",
    val stock: Int = 100
)

@Serializable
data class ProductCreateRequest(
    val name: String,
    val imageUrl: String,
    val rating: Float,
    val price: Double,
    val description: String = "",
    val category: String = "General",
    val stock: Int = 100
)

@Serializable
data class ProductUpdateRequest(
    val name: String? = null,
    val imageUrl: String? = null,
    val rating: Float? = null,
    val price: Double? = null,
    val description: String? = null,
    val category: String? = null,
    val stock: Int? = null
)

@Serializable
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class PaginatedResponse<T>(
    val success: Boolean,
    val data: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int,
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class ErrorResponse(
    val success: Boolean = false,
    val error: String,
    val message: String,
    val timestamp: Long = System.currentTimeMillis()
)

