# Serialization Exception Fix

## Problem

The Ktor server was throwing `SerializationException` errors when trying to respond with Map objects:

```
kotlinx.serialization.SerializationException: Serializer for subclass 'LinkedHashMap' is not found in the polymorphic scope of 'Map'
```

This occurred on endpoints like `/health` and `/` that were using `mapOf()` to create response objects.

## Root Cause

When using Ktor's `ContentNegotiation` plugin with Kotlinx Serialization, all response objects must be serializable. Plain Kotlin `Map` objects (which become `LinkedHashMap` at runtime) are **not serializable by default** in Kotlinx Serialization.

The code was doing:
```kotlin
get("/health") {
    call.respond(
        mapOf(
            "status" to "UP",
            "timestamp" to System.currentTimeMillis()
        )
    )
}
```

## Solution

Created proper `@Serializable` data classes for all responses:

### 1. Added Response Models

**File:** `product-apis/src/main/kotlin/com/ai/agent/productapis/models/Models.kt`

```kotlin
@Serializable
data class HealthResponse(
    val status: String,
    val timestamp: Long = System.currentTimeMillis()
)

@Serializable
data class InfoResponse(
    val name: String,
    val version: String,
    val status: String,
    val timestamp: Long = System.currentTimeMillis()
)
```

### 2. Updated Application.kt

**File:** `product-apis/src/main/kotlin/com/ai/agent/productapis/Application.kt`

Changed from:
```kotlin
call.respond(mapOf("status" to "UP", "timestamp" to System.currentTimeMillis()))
```

To:
```kotlin
call.respond(HealthResponse(status = "UP", timestamp = System.currentTimeMillis()))
```

### 3. Updated Plugins.kt

**File:** `product-apis/src/main/kotlin/com/ai/agent/productapis/plugins/Plugins.kt`

Replaced all `mapOf()` error responses with `ErrorResponse` data class instances.

## Testing

All endpoints now work correctly:

```bash
# Health check
curl http://localhost:8080/health
# Response: {"status":"UP","timestamp":1762367681287}

# Info endpoint
curl http://localhost:8080/
# Response: {"name":"Product APIs","version":"1.0.0","status":"running","timestamp":1762367714216}

# Products API
curl http://localhost:8080/api/v1/products
# Returns properly serialized product list
```

## Key Takeaway

When using Ktor with Kotlinx Serialization's `ContentNegotiation` plugin:
- ✅ Always use `@Serializable` data classes for responses
- ❌ Never use plain `mapOf()` or `Map` objects in `call.respond()`

This ensures proper JSON serialization and prevents runtime serialization exceptions.

