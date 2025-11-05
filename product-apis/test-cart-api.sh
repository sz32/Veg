#!/bin/bash

# Test script for Cart API endpoints
BASE_URL="http://localhost:8080"

echo "=========================================="
echo "Testing Cart API Endpoints"
echo "=========================================="
echo ""

# Test 1: Get empty cart
echo "1. GET /api/v1/cart - Get empty cart"
curl -s -X GET "$BASE_URL/api/v1/cart" | jq '.'
echo ""
echo ""

# Test 2: Add first product to cart
echo "2. POST /api/v1/cart/items - Add product ID 1 with quantity 2"
curl -s -X POST "$BASE_URL/api/v1/cart/items" \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}' | jq '.'
echo ""
echo ""

# Test 3: Add another product to cart
echo "3. POST /api/v1/cart/items - Add product ID 2 with quantity 1"
curl -s -X POST "$BASE_URL/api/v1/cart/items" \
  -H "Content-Type: application/json" \
  -d '{"productId": 2, "quantity": 1}' | jq '.'
echo ""
echo ""

# Test 4: Get cart with items
echo "4. GET /api/v1/cart - Get cart with items"
curl -s -X GET "$BASE_URL/api/v1/cart" | jq '.'
echo ""
echo ""

# Test 5: Add same product again (should increase quantity)
echo "5. POST /api/v1/cart/items - Add product ID 1 again with quantity 1"
curl -s -X POST "$BASE_URL/api/v1/cart/items" \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 1}' | jq '.'
echo ""
echo ""

# Test 6: Get specific cart item
echo "6. GET /api/v1/cart/items/1 - Get cart item with ID 1"
curl -s -X GET "$BASE_URL/api/v1/cart/items/1" | jq '.'
echo ""
echo ""

# Test 7: Update cart item quantity
echo "7. PUT /api/v1/cart/items/1 - Update cart item quantity to 5"
curl -s -X PUT "$BASE_URL/api/v1/cart/items/1" \
  -H "Content-Type: application/json" \
  -d '{"quantity": 5}' | jq '.'
echo ""
echo ""

# Test 8: Get updated cart
echo "8. GET /api/v1/cart - Get cart after update"
curl -s -X GET "$BASE_URL/api/v1/cart" | jq '.'
echo ""
echo ""

# Test 9: Remove cart item
echo "9. DELETE /api/v1/cart/items/2 - Remove cart item with ID 2"
curl -s -X DELETE "$BASE_URL/api/v1/cart/items/2" | jq '.'
echo ""
echo ""

# Test 10: Get cart after deletion
echo "10. GET /api/v1/cart - Get cart after deletion"
curl -s -X GET "$BASE_URL/api/v1/cart" | jq '.'
echo ""
echo ""

# Test 11: Clear entire cart
echo "11. DELETE /api/v1/cart - Clear entire cart"
curl -s -X DELETE "$BASE_URL/api/v1/cart" | jq '.'
echo ""
echo ""

# Test 12: Verify empty cart
echo "12. GET /api/v1/cart - Verify cart is empty"
curl -s -X GET "$BASE_URL/api/v1/cart" | jq '.'
echo ""
echo ""

# Error Test 1: Invalid quantity
echo "13. POST /api/v1/cart/items - Try to add with invalid quantity (0)"
curl -s -X POST "$BASE_URL/api/v1/cart/items" \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 0}' | jq '.'
echo ""
echo ""

# Error Test 2: Non-existent product
echo "14. POST /api/v1/cart/items - Try to add non-existent product"
curl -s -X POST "$BASE_URL/api/v1/cart/items" \
  -H "Content-Type: application/json" \
  -d '{"productId": 99999, "quantity": 1}' | jq '.'
echo ""
echo ""

echo "=========================================="
echo "Cart API Testing Complete"
echo "=========================================="

