package com.ai.agent.productapis.routes

import com.ai.agent.productapis.models.*
import com.ai.agent.productapis.repository.CartRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.cartRoutes(repository: CartRepository) {
    route("/api/v1/cart") {

        // GET /api/v1/cart - Get cart details with all items
        get {
            val cart = repository.getCart()
            call.respond(
                HttpStatusCode.OK,
                ApiResponse(success = true, data = cart)
            )
        }

        // POST /api/v1/cart/items - Add item to cart
        post("/items") {
            try {
                val request = call.receive<AddToCartRequest>()

                // Validation
                if (request.quantity <= 0) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(
                            error = "VALIDATION_ERROR",
                            message = "Quantity must be greater than 0"
                        )
                    )
                    return@post
                }

                val cartItem = repository.addToCart(request.productId, request.quantity)
                if (cartItem == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(
                            error = "INVALID_REQUEST",
                            message = "Could not add product to cart. Product may not exist or insufficient stock."
                        )
                    )
                    return@post
                }

                call.respond(
                    HttpStatusCode.Created,
                    ApiResponse(
                        success = true,
                        data = cartItem,
                        message = "Product added to cart successfully"
                    )
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(
                        error = "BAD_REQUEST",
                        message = "Invalid request format: ${e.message}"
                    )
                )
            }
        }

        // DELETE /api/v1/cart/items/{id} - Remove item from cart
        delete("/items/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(error = "BAD_REQUEST", message = "Invalid cart item ID")
                )
                return@delete
            }

            val removed = repository.removeFromCart(id)
            if (!removed) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(
                        error = "NOT_FOUND",
                        message = "Cart item with ID $id not found"
                    )
                )
                return@delete
            }

            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    success = true,
                    data = null,
                    message = "Item removed from cart successfully"
                )
            )
        }

        // PUT /api/v1/cart/items/{id} - Update cart item quantity
        put("/items/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(error = "BAD_REQUEST", message = "Invalid cart item ID")
                )
                return@put
            }

            try {
                val request = call.receive<UpdateCartItemRequest>()

                if (request.quantity <= 0) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(
                            error = "VALIDATION_ERROR",
                            message = "Quantity must be greater than 0"
                        )
                    )
                    return@put
                }

                val updatedItem = repository.updateCartItemQuantity(id, request.quantity)
                if (updatedItem == null) {
                    call.respond(
                        HttpStatusCode.BadRequest,
                        ErrorResponse(
                            error = "INVALID_REQUEST",
                            message = "Could not update cart item. Item may not exist or insufficient stock."
                        )
                    )
                    return@put
                }

                call.respond(
                    HttpStatusCode.OK,
                    ApiResponse(
                        success = true,
                        data = updatedItem,
                        message = "Cart item updated successfully"
                    )
                )
            } catch (e: Exception) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(
                        error = "BAD_REQUEST",
                        message = "Invalid request format: ${e.message}"
                    )
                )
            }
        }

        // GET /api/v1/cart/items/{id} - Get specific cart item
        get("/items/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(
                    HttpStatusCode.BadRequest,
                    ErrorResponse(error = "BAD_REQUEST", message = "Invalid cart item ID")
                )
                return@get
            }

            val cartItem = repository.getCartItem(id)
            if (cartItem == null) {
                call.respond(
                    HttpStatusCode.NotFound,
                    ErrorResponse(
                        error = "NOT_FOUND",
                        message = "Cart item with ID $id not found"
                    )
                )
                return@get
            }

            call.respond(
                HttpStatusCode.OK,
                ApiResponse(success = true, data = cartItem)
            )
        }

        // DELETE /api/v1/cart - Clear entire cart
        delete {
            repository.clearCart()
            call.respond(
                HttpStatusCode.OK,
                ApiResponse(
                    success = true,
                    data = null,
                    message = "Cart cleared successfully"
                )
            )
        }
    }
}

