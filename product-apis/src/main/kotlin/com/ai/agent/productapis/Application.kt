package com.ai.agent.productapis

import com.ai.agent.productapis.plugins.*
import com.ai.agent.productapis.repository.ProductRepository
import com.ai.agent.productapis.routes.productRoutes
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(
        Netty,
        port = System.getenv("PORT")?.toIntOrNull() ?: 8080,
        host = "0.0.0.0",
        module = Application::module
    ).start(wait = true)
}

fun Application.module() {
    // Initialize repository
    val productRepository = ProductRepository()

    // Configure plugins
    configureHTTP()
    configureSerialization()
    configureMonitoring()
    configureStatusPages()

    // Configure routing
    routing {
        // Health check endpoint
        get("/") {
            call.respond(
                mapOf(
                    "name" to "Product APIs",
                    "version" to "1.0.0",
                    "status" to "running",
                    "timestamp" to System.currentTimeMillis()
                )
            )
        }

        get("/health") {
            call.respond(
                mapOf(
                    "status" to "UP",
                    "timestamp" to System.currentTimeMillis()
                )
            )
        }

        // API routes
        productRoutes(productRepository)
    }

    // Log startup
    environment.log.info("Product APIs server started on port ${environment.config.port}")
    environment.log.info("Available endpoints:")
    environment.log.info("  GET  /")
    environment.log.info("  GET  /health")
    environment.log.info("  GET  /api/v1/products")
    environment.log.info("  GET  /api/v1/products/{id}")
    environment.log.info("  POST /api/v1/products")
    environment.log.info("  PUT  /api/v1/products/{id}")
    environment.log.info("  DELETE /api/v1/products/{id}")
    environment.log.info("  GET  /api/v1/products/categories/list")
}

