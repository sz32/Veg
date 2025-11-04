# Product APIs - Ktor Backend Service

A RESTful API service built with Ktor for managing product data. This is a pure Kotlin module with no Android dependencies.

## 🚀 Features

- **RESTful API** endpoints for product management
- **CRUD operations** (Create, Read, Update, Delete)
- **Pagination** support for product listings
- **Search & Filter** by category and keywords
- **In-memory data storage** with sample products
- **CORS enabled** for cross-origin requests
- **Request validation** and error handling
- **Logging** with Logback
- **JSON serialization** with kotlinx.serialization

## 📋 Prerequisites

- **Java 17** or higher
- **Gradle 8.0** or higher
- **Kotlin 1.9.20** or higher

## 🏗️ Project Structure

```
product-apis/
├── src/
│   └── main/
│       ├── kotlin/
│       │   └── com/ai/agent/productapis/
│       │       ├── Application.kt          # Main entry point
│       │       ├── models/
│       │       │   └── Models.kt           # Data models
│       │       ├── repository/
│       │       │   └── ProductRepository.kt # Data management
│       │       ├── routes/
│       │       │   └── ProductRoutes.kt    # API endpoints
│       │       └── plugins/
│       │           └── Plugins.kt          # Ktor plugins configuration
│       └── resources/
│           ├── application.conf            # Ktor configuration
│           └── logback.xml                 # Logging configuration
├── build.gradle.kts                        # Build configuration
└── README.md                               # This file
```

## 🔧 Installation & Setup

### 1. Clone/Navigate to the Project

```bash
cd /Users/zeel/StudioProjects/FullCreative/Personal/AiAgent
```

### 2. Build the Project

```bash
# Build the entire project (including product-apis module)
./gradlew :product-apis:build

# Or build just the product-apis module
./gradlew :product-apis:clean :product-apis:build
```

### 3. Run the Server Locally

```bash
# Run using Gradle
./gradlew :product-apis:run

# Or use the application plugin
./gradlew :product-apis:runShadow
```

The server will start at `http://localhost:8080`

### 4. Alternative: Build and Run Fat JAR

```bash
# Build fat JAR
./gradlew :product-apis:fatJar

# Run the JAR
java -jar product-apis/build/libs/product-apis-1.0.0.jar
```

## 📡 API Endpoints

### Base URL
```
http://localhost:8080
```

### Health Check Endpoints

#### 1. Root Endpoint
```http
GET /
```
**Response:**
```json
{
  "name": "Product APIs",
  "version": "1.0.0",
  "status": "running",
  "timestamp": 1699200000000
}
```

#### 2. Health Check
```http
GET /health
```
**Response:**
```json
{
  "status": "UP",
  "timestamp": 1699200000000
}
```

### Product Endpoints

#### 1. Get All Products
```http
GET /api/v1/products
```

**Query Parameters:**
- `page` (optional): Page number (default: 1)
- `pageSize` (optional): Items per page (default: 10)
- `category` (optional): Filter by category
- `search` (optional): Search in name, description, or category

**Examples:**
```bash
# Get all products (paginated)
curl http://localhost:8080/api/v1/products

# Get products with custom pagination
curl http://localhost:8080/api/v1/products?page=1&pageSize=5

# Filter by category
curl http://localhost:8080/api/v1/products?category=Electronics

# Search products
curl http://localhost:8080/api/v1/products?search=laptop
```

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "name": "Wireless Headphones",
      "imageUrl": "https://picsum.photos/seed/product1/400/400",
      "rating": 4.5,
      "price": 99.99,
      "description": "Premium wireless headphones...",
      "category": "Electronics",
      "stock": 50
    }
  ],
  "page": 1,
  "pageSize": 10,
  "totalItems": 10,
  "totalPages": 1,
  "timestamp": 1699200000000
}
```

#### 2. Get Product by ID
```http
GET /api/v1/products/{id}
```

**Example:**
```bash
curl http://localhost:8080/api/v1/products/1
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Wireless Headphones",
    "imageUrl": "https://picsum.photos/seed/product1/400/400",
    "rating": 4.5,
    "price": 99.99,
    "description": "Premium wireless headphones...",
    "category": "Electronics",
    "stock": 50
  },
  "message": null,
  "timestamp": 1699200000000
}
```

#### 3. Create Product
```http
POST /api/v1/products
Content-Type: application/json
```

**Request Body:**
```json
{
  "name": "New Product",
  "imageUrl": "https://example.com/image.jpg",
  "rating": 4.5,
  "price": 149.99,
  "description": "Product description",
  "category": "Electronics",
  "stock": 100
}
```

**Example:**
```bash
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Laptop",
    "imageUrl": "https://picsum.photos/seed/laptop/400/400",
    "rating": 4.8,
    "price": 1299.99,
    "description": "High-performance gaming laptop",
    "category": "Computers",
    "stock": 15
  }'
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 11,
    "name": "Gaming Laptop",
    "imageUrl": "https://picsum.photos/seed/laptop/400/400",
    "rating": 4.8,
    "price": 1299.99,
    "description": "High-performance gaming laptop",
    "category": "Computers",
    "stock": 15
  },
  "message": "Product created successfully",
  "timestamp": 1699200000000
}
```

#### 4. Update Product
```http
PUT /api/v1/products/{id}
Content-Type: application/json
```

**Request Body** (all fields optional):
```json
{
  "name": "Updated Name",
  "price": 199.99,
  "stock": 50
}
```

**Example:**
```bash
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{
    "price": 89.99,
    "stock": 45
  }'
```

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "name": "Wireless Headphones",
    "imageUrl": "https://picsum.photos/seed/product1/400/400",
    "rating": 4.5,
    "price": 89.99,
    "description": "Premium wireless headphones...",
    "category": "Electronics",
    "stock": 45
  },
  "message": "Product updated successfully",
  "timestamp": 1699200000000
}
```

#### 5. Delete Product
```http
DELETE /api/v1/products/{id}
```

**Example:**
```bash
curl -X DELETE http://localhost:8080/api/v1/products/1
```

**Response:**
```json
{
  "success": true,
  "data": null,
  "message": "Product deleted successfully",
  "timestamp": 1699200000000
}
```

#### 6. Get Categories
```http
GET /api/v1/products/categories/list
```

**Example:**
```bash
curl http://localhost:8080/api/v1/products/categories/list
```

**Response:**
```json
{
  "success": true,
  "data": [
    "Accessories",
    "Audio",
    "Computers",
    "Displays",
    "Electronics",
    "Storage"
  ],
  "message": null,
  "timestamp": 1699200000000
}
```

## 🌐 Deployment

### Option 1: Deploy to Heroku

#### Prerequisites
- Heroku CLI installed
- Heroku account

#### Steps

1. **Create a Heroku app**
```bash
heroku create your-app-name
```

2. **Create a Procfile** in the project root:
```bash
cd product-apis
cat > Procfile << EOF
web: java -jar build/libs/product-apis-1.0.0.jar
EOF
```

3. **Add system.properties** to specify Java version:
```bash
cat > system.properties << EOF
java.runtime.version=17
EOF
```

4. **Build the fat JAR**
```bash
cd ..
./gradlew :product-apis:fatJar
```

5. **Deploy to Heroku**
```bash
git add .
git commit -m "Deploy product APIs"
git push heroku main
```

6. **Open your app**
```bash
heroku open
```

### Option 2: Deploy to Railway

1. **Install Railway CLI**
```bash
npm i -g @railway/cli
```

2. **Login to Railway**
```bash
railway login
```

3. **Initialize Railway project**
```bash
cd product-apis
railway init
```

4. **Deploy**
```bash
railway up
```

5. **Get the URL**
```bash
railway domain
```

### Option 3: Deploy to Render

1. Go to [Render.com](https://render.com)
2. Create a new **Web Service**
3. Connect your GitHub repository
4. Configure:
   - **Build Command**: `./gradlew :product-apis:fatJar`
   - **Start Command**: `java -jar product-apis/build/libs/product-apis-1.0.0.jar`
   - **Environment**: `PORT=8080`
5. Click **Create Web Service**

### Option 4: Deploy to Docker

1. **Create Dockerfile** in `product-apis/`:
```dockerfile
FROM gradle:8.4-jdk17 AS build
WORKDIR /app
COPY . .
RUN gradle :product-apis:fatJar --no-daemon

FROM openjdk:17-slim
WORKDIR /app
COPY --from=build /app/product-apis/build/libs/product-apis-1.0.0.jar app.jar
EXPOSE 8080
ENV PORT=8080
CMD ["java", "-jar", "app.jar"]
```

2. **Build Docker image**
```bash
docker build -t product-apis .
```

3. **Run Docker container**
```bash
docker run -p 8080:8080 product-apis
```

4. **Deploy to Docker Hub**
```bash
docker tag product-apis yourusername/product-apis:latest
docker push yourusername/product-apis:latest
```

### Option 5: Deploy to AWS EC2

1. **Launch EC2 instance** (Amazon Linux 2 or Ubuntu)

2. **SSH into instance**
```bash
ssh -i your-key.pem ec2-user@your-instance-ip
```

3. **Install Java 17**
```bash
sudo yum install java-17-amazon-corretto -y
# or for Ubuntu
sudo apt update && sudo apt install openjdk-17-jdk -y
```

4. **Transfer JAR file**
```bash
scp -i your-key.pem product-apis/build/libs/product-apis-1.0.0.jar ec2-user@your-instance-ip:~/
```

5. **Run the application**
```bash
nohup java -jar product-apis-1.0.0.jar > app.log 2>&1 &
```

6. **Configure Security Group** to allow inbound traffic on port 8080

7. **Access your API** at `http://your-instance-ip:8080`

## 🔒 Production Configuration

For production deployment, update these configurations:

### 1. CORS Configuration
In `Plugins.kt`, replace `anyHost()` with specific hosts:
```kotlin
install(CORS) {
    allowHost("yourdomain.com", schemes = listOf("https"))
    allowHost("www.yourdomain.com", schemes = listOf("https"))
    // ... rest of configuration
}
```

### 2. Environment Variables
Set these environment variables:
```bash
export PORT=8080
export ENVIRONMENT=production
```

### 3. Database Integration
For persistent storage, integrate a database:
- PostgreSQL
- MongoDB
- MySQL

## 🧪 Testing

### Test with cURL

```bash
# Health check
curl http://localhost:8080/health

# Get all products
curl http://localhost:8080/api/v1/products

# Get specific product
curl http://localhost:8080/api/v1/products/1

# Create product
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Product","imageUrl":"https://example.com/img.jpg","rating":4.0,"price":99.99}'
```

### Test with Postman

1. Import the API endpoints into Postman
2. Set base URL: `http://localhost:8080`
3. Test each endpoint with different request bodies

## 📦 Dependencies

- **Ktor 2.3.7** - Web framework
- **Kotlinx Serialization 1.6.2** - JSON serialization
- **Logback 1.4.14** - Logging

## 🤝 Integration with Android App

To integrate this API with your Android app:

1. **Update base URL** in your Android app:
```kotlin
const val BASE_URL = "http://your-server-url:8080"
```

2. **Use Retrofit or Ktor Client** to make API calls:
```kotlin
// Example with Retrofit
interface ProductApiService {
    @GET("/api/v1/products")
    suspend fun getProducts(): Response<PaginatedResponse<Product>>
}
```

## 📝 License

This project is part of the Ai Agent application.

## 👨‍💻 Author

Created for the Ai Agent Android application project.

---

**Need help?** Check the logs in `logs/product-apis.log` for debugging information.

