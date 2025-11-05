# Cart API - Quick Reference

## Base URL
```
http://localhost:8080/api/v1/cart
```

## Endpoints Summary

| Method | Endpoint                  | Description      |
|--------|---------------------------|------------------|
| GET    | `/api/v1/cart`            | Get entire cart  |
| POST   | `/api/v1/cart/items`      | Add item to cart |
| GET    | `/api/v1/cart/items/{id}` | Get cart item    |
| PUT    | `/api/v1/cart/items/{id}` | Update quantity  |
| DELETE | `/api/v1/cart/items/{id}` | Remove item      |
| DELETE | `/api/v1/cart`            | Clear cart       |

## Quick Commands

### Get Cart
```bash
curl http://localhost:8080/api/v1/cart
```

### Add Item
```bash
curl -X POST http://localhost:8080/api/v1/cart/items \
  -H "Content-Type: application/json" \
  -d '{"productId": 1, "quantity": 2}'
```

### Update Item
```bash
curl -X PUT http://localhost:8080/api/v1/cart/items/1 \
  -H "Content-Type: application/json" \
  -d '{"quantity": 5}'
```

### Remove Item
```bash
curl -X DELETE http://localhost:8080/api/v1/cart/items/1
```

### Clear Cart
```bash
curl -X DELETE http://localhost:8080/api/v1/cart
```

## Response Structure

### Success
```json
{
  "success": true,
  "data": { /* cart or item data */ },
  "message": "Optional message",
  "timestamp": 1699276800000
}
```

### Error
```json
{
  "success": false,
  "error": "ERROR_CODE",
  "message": "Error description",
  "timestamp": 1699276800000
}
```

## Cart Object
```json
{
  "items": [
    {
      "id": 1,
      "productId": 1,
      "product": { /* product details */ },
      "quantity": 2,
      "addedAt": 1699276800000
    }
  ],
  "totalItems": 2,
  "totalPrice": 199.98,
  "timestamp": 1699276800000
}
```

## Testing
```bash
# Run automated tests
cd product-apis
./test-cart-api.sh
```

## Documentation
- Full API docs: `docs/CART_API_DOCUMENTATION.md`
- Implementation: `docs/CART_IMPLEMENTATION_SUMMARY.md`

