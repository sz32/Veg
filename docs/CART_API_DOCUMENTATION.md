# Cart API Documentation

This document describes the Cart API endpoints that have been added to the Product APIs.

## Overview

The Cart API allows users to:
- Add products to a shopping cart
- Remove items from the cart
- Update item quantities
- View the cart contents
- Clear the entire cart

## Base URL

All cart endpoints are prefixed with: `/api/v1/cart`

## Endpoints

### 1. Get Cart

Get the entire cart with all items and totals.

**Endpoint:** `GET /api/v1/cart`

**Response:**
```json
{
  "success": true,
  "data": {
    "items": [
      {
        "id": 1,
        "productId": 1,
        "product": {
          "id": 1,
          "name": "Wireless Headphones",
          "imageUrl": "https://picsum.photos/seed/product1/400/400",
          "rating": 4.5,
          "price": 99.99,
          "description": "Premium wireless headphones...",
          "category": "Electronics",
          "stock": 50
        },
        "quantity": 2,
        "addedAt": 1699276800000
      }
    ],
    "totalItems": 2,
    "totalPrice": 199.98,
    "timestamp": 1699276800000
  },
  "timestamp": 1699276800000
}
```

---

### 2. Add Item to Cart

Add a product to the cart or increase quantity if already exists.

**Endpoint:** `POST /api/v1/cart/items`

**Request Body:**
```json
{
  "productId": 1,
  "quantity": 2
}
```

**Response (Success):**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "productId": 1,
    "product": {
      "id": 1,
      "name": "Wireless Headphones",
      "imageUrl": "https://picsum.photos/seed/product1/400/400",
      "rating": 4.5,
      "price": 99.99,
      "description": "Premium wireless headphones...",
      "category": "Electronics",
      "stock": 50
    },
    "quantity": 2,
    "addedAt": 1699276800000
  },
  "message": "Product added to cart successfully",
  "timestamp": 1699276800000
}
```

**Error Responses:**

- **400 Bad Request** - Invalid quantity (must be > 0)
```json
{
  "success": false,
  "error": "VALIDATION_ERROR",
  "message": "Quantity must be greater than 0",
  "timestamp": 1699276800000
}
```

- **400 Bad Request** - Product not found or insufficient stock
```json
{
  "success": false,
  "error": "INVALID_REQUEST",
  "message": "Could not add product to cart. Product may not exist or insufficient stock.",
  "timestamp": 1699276800000
}
```

---

### 3. Remove Item from Cart

Remove a specific item from the cart.

**Endpoint:** `DELETE /api/v1/cart/items/{id}`

**Path Parameters:**
- `id` (integer) - The cart item ID

**Response (Success):**
```json
{
  "success": true,
  "data": null,
  "message": "Item removed from cart successfully",
  "timestamp": 1699276800000
}
```

**Error Responses:**

- **400 Bad Request** - Invalid cart item ID
```json
{
  "success": false,
  "error": "BAD_REQUEST",
  "message": "Invalid cart item ID",
  "timestamp": 1699276800000
}
```

- **404 Not Found** - Cart item not found
```json
{
  "success": false,
  "error": "NOT_FOUND",
  "message": "Cart item with ID 1 not found",
  "timestamp": 1699276800000
}
```

---

### 4. Update Cart Item Quantity

Update the quantity of a specific cart item.

**Endpoint:** `PUT /api/v1/cart/items/{id}`

**Path Parameters:**
- `id` (integer) - The cart item ID

**Request Body:**
```json
{
  "quantity": 5
}
```

**Response (Success):**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "productId": 1,
    "product": {
      "id": 1,
      "name": "Wireless Headphones",
      "imageUrl": "https://picsum.photos/seed/product1/400/400",
      "rating": 4.5,
      "price": 99.99,
      "description": "Premium wireless headphones...",
      "category": "Electronics",
      "stock": 50
    },
    "quantity": 5,
    "addedAt": 1699276800000
  },
  "message": "Cart item updated successfully",
  "timestamp": 1699276800000
}
```

**Error Responses:**

- **400 Bad Request** - Invalid quantity
```json
{
  "success": false,
  "error": "VALIDATION_ERROR",
  "message": "Quantity must be greater than 0",
  "timestamp": 1699276800000
}
```

- **400 Bad Request** - Insufficient stock or item not found
```json
{
  "success": false,
  "error": "INVALID_REQUEST",
  "message": "Could not update cart item. Item may not exist or insufficient stock.",
  "timestamp": 1699276800000
}
```

---

### 5. Get Specific Cart Item

Retrieve details of a specific cart item.

**Endpoint:** `GET /api/v1/cart/items/{id}`

**Path Parameters:**
- `id` (integer) - The cart item ID

**Response (Success):**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "productId": 1,
    "product": {
      "id": 1,
      "name": "Wireless Headphones",
      "imageUrl": "https://picsum.photos/seed/product1/400/400",
      "rating": 4.5,
      "price": 99.99,
      "description": "Premium wireless headphones...",
      "category": "Electronics",
      "stock": 50
    },
    "quantity": 2,
    "addedAt": 1699276800000
  },
  "timestamp": 1699276800000
}
```

**Error Responses:**

- **404 Not Found** - Cart item not found
```json
{
  "success": false,
  "error": "NOT_FOUND",
  "message": "Cart item with ID 1 not found",
  "timestamp": 1699276800000
}
```

---

### 6. Clear Cart

Remove all items from the cart.

**Endpoint:** `DELETE /api/v1/cart`

**Response:**
```json
{
  "success": true,
  "data": null,
  "message": "Cart cleared successfully",
  "timestamp": 1699276800000
}
```

---

## Data Models

### CartItem
```typescript
{
  id: number,              // Unique cart item ID
  productId: number,       // ID of the product
  product: Product,        // Full product details
  quantity: number,        // Quantity in cart
  addedAt: number         // Timestamp when added
}
```

### Cart
```typescript
{
  items: CartItem[],      // Array of cart items
  totalItems: number,     // Total quantity of all items
  totalPrice: number,     // Total price of all items
  timestamp: number       // Current timestamp
}
```

### AddToCartRequest
```typescript
{
  productId: number,      // ID of product to add
  quantity: number        // Quantity to add (default: 1)
}
```

### UpdateCartItemRequest
```typescript
{
  quantity: number        // New quantity
}
```

---

## Business Rules

1. **Quantity Validation**: Quantity must be greater than 0
2. **Stock Validation**: Cannot add/update quantity beyond available stock
3. **Duplicate Products**: Adding an existing product increases its quantity
4. **Product Validation**: Product must exist before adding to cart
5. **Auto-calculation**: Total items and total price are automatically calculated

---

## Example Usage

### Example 1: Add Product to Cart
```bash
curl -X POST http://localhost:8080/api/v1/cart/items \
  -H "Content-Type: application/json" \
  -d '{
    "productId": 1,
    "quantity": 2
  }'
```

### Example 2: Get Cart
```bash
curl http://localhost:8080/api/v1/cart
```

### Example 3: Update Cart Item
```bash
curl -X PUT http://localhost:8080/api/v1/cart/items/1 \
  -H "Content-Type: application/json" \
  -d '{
    "quantity": 3
  }'
```

### Example 4: Remove Item from Cart
```bash
curl -X DELETE http://localhost:8080/api/v1/cart/items/1
```

### Example 5: Clear Cart
```bash
curl -X DELETE http://localhost:8080/api/v1/cart
```

---

## Testing

You can test the cart APIs using the included `api-tester.html` file in the `product-apis` directory. Simply open it in a browser and use the cart endpoints section.

## Notes

- The cart is stored in-memory and will be cleared when the server restarts
- Each cart item has a unique ID separate from the product ID
- The cart is shared across all users (single cart for demonstration purposes)
- For production use, implement user-specific carts with authentication

