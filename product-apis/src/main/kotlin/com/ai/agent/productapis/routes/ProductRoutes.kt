package com.ai.agent.productapis.routes

import com.ai.agent.productapis.models.*
import com.ai.agent.productapis.repository.ProductRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlin.math.ceil

fun Route.productRoutes(repository: ProductRepository) {
    route("/api/v1/products") {

        // GET /api/v1/products - Get all products with optional pagination and search
        get {
            val page = call.request.queryParameters["page"]?.toIntOrNull() ?: 1
            val pageSize = call.request.queryParameters["pageSize"]?.toIntOrNull() ?: 10
            val category = call.request.queryParameters["category"]
            val search = call.request.queryParameters["search"]

            val products = when {
                !search.isNullOrBlank() -> repository.searchProducts(search)
                !category.isNullOrBlank() -> repository.getProductsByCategory(category)
                else -> repository.getAllProducts()
            }

            if (page > 0 && pageSize > 0) {
                val totalItems = products.size
                val totalPages = ceil(totalItems.toDouble() / pageSize).toInt()
                val startIndex = (page - 1) * pageSize
                val endIndex = minOf(startIndex + pageSize, totalItems)

                val paginatedProducts = if (startIndex < totalItems) {
                    products.subList(startIndex, endIndex)
                } else {
                    emptyList()
                }

                call.respond(
                    HttpStatusCode.OK,
                    PaginatedResponse(
                        success = true,
                        data = paginatedProducts,
                        page = page,
                        pageSize = pageSize,
                        totalItems = totalItems,
                        totalPages = totalPages
                    )
                )
            } else {
                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse(success = true, data = products)
                )
            }
        }

        // GET /api/v1/products/{id} - Get product by ID
        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(error = "BAD_REQUEST", message = "Invalid product ID")
                )
                return@get
            }

            val product = repository.getProductById(id)
            if (product == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(error = "NOT_FOUND", message = "Product with ID $id not found")
                )
                return@get
            }

            call.respond(
                HttpStatusCode.OK,
                ApiResponse(success = true, data = product)
            )
        }

        // POST /api/v1/products - Create new product
        post {
            try {
                val request = call.receive<ProductCreateRequest>()

                // Validation
                if (request.name.isBlank()) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(error = "VALIDATION_ERROR", message = "Product name cannot be empty")
                    )
                    return@post
                }

                if (request.price <= 0) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(error = "VALIDATION_ERROR", message = "Product price must be greater than 0")
                    )
                    return@post
                }

                if (request.rating < 0 || request.rating > 5) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(error = "VALIDATION_ERROR", message = "Product rating must be between 0 and 5")
                    )
                    return@post
                }

                val product = repository.createProduct(request)
                call.respond(
                    HttpStatusCode.Created,
                    ApiResponse(
                        success = true,
                        data = product,
                        message = "Product created successfully"
                    )
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(error = "BAD_REQUEST", message = "Invalid request body: ${e.message}")
                )
            }
        }

        // PUT /api/v1/products/{id} - Update product
        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(error = "BAD_REQUEST", message = "Invalid product ID")
                )
                return@put
            }

            try {
                val request = call.receive<ProductUpdateRequest>()

                // Validation
                if (request.price != null && request.price <= 0) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(error = "VALIDATION_ERROR", message = "Product price must be greater than 0")
                    )
                    return@put
                }

                if (request.rating != null && (request.rating < 0 || request.rating > 5)) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(error = "VALIDATION_ERROR", message = "Product rating must be between 0 and 5")
                    )
                    return@put
                }

                val updatedProduct = repository.updateProduct(id, request)
                if (updatedProduct == null) {
                    call.respond(
                        HttpStatusCode.NotFound,
                        ErrorResponse(error = "NOT_FOUND", message = "Product with ID $id not found")
                    )
                    return@put
                }

                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse(
                        success = true,
                        data = updatedProduct,
                        message = "Product updated successfully"
                    )
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(error = "BAD_REQUEST", message = "Invalid request body: ${e.message}")
                )
            }
        }

        // DELETE /api/v1/products/{id} - Delete product
        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(error = "BAD_REQUEST", message = "Invalid product ID")
                )
                return@delete
            }

            val deleted = repository.deleteProduct(id)
            if (!deleted) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(error = "NOT_FOUND", message = "Product with ID $id not found")
                )
                return@delete
            }

            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    success = true,
                    data = null,
                    message = "Product deleted successfully"
                )
            )
        }

        // GET /api/v1/products/categories - Get all categories
        get("/categories/list") {
            val categories = repository.getCategories()
            call.respond(
                HttpStatusCode.OK,
                ApiResponse(success = true, data = categories)
            )
        }
    }
}

