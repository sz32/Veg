# 🛍️ AiAgent - E-Commerce Android Application

A modern, feature-rich e-commerce Android application built with **Jetpack Compose**, **Kotlin**, **Room Database**, and **Ktor** backend APIs. This project demonstrates best practices in Android development with multi-language support, Material Design 3, and a comprehensive backend service.

[![Kotlin](https://img.shields.io/badge/Kotlin-1.9.20-blue.svg)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Latest-green.svg)](https://developer.android.com/jetpack/compose)
[![Material Design 3](https://img.shields.io/badge/Material%20Design-3-orange.svg)](https://m3.material.io/)
[![License](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)

---

## 📑 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Product APIs - Routes & Architecture](#-product-apis---routes--architecture)
- [Getting Started](#-getting-started)
- [Documentation](#-documentation)
- [Screenshots](#-screenshots)
- [Contributing](#-contributing)
- [License](#-license)

---

## ✨ Features

### 🎨 **User Interface**
- **Material Design 3** with dynamic theming
- **Dark/Light/System Theme** support
- Smooth animations and transitions
- **Bounce animations** for interactive elements
- Swipe-to-delete gestures with visual feedback
- Responsive layouts optimized for different screen sizes

### 🌍 **Multi-Language Support**
Fully localized in 4 languages:
- 🇬🇧 English
- 🇮🇳 Hindi (हिन्दी)
- 🇮🇳 Gujarati (ગુજરાતી)
- 🇮🇳 Marathi (मराठी)

### 🛒 **E-Commerce Features**
- Product browsing with search and filters
- Detailed product view with ratings and descriptions
- Shopping cart management
- Add/Remove items with smooth animations
- Persistent cart storage using Room Database
- Checkout functionality
- Real-time price calculations

### ⚙️ **Settings Module**
- Language selection with live preview
- Theme switching (Light/Dark/System)
- User preferences management using DataStore
- Module-based architecture for reusability

### 🔄 **Backend Integration**
- RESTful API built with Ktor
- CRUD operations for products
- Pagination and search capabilities
- Real-time data synchronization

---

## 🛠️ Tech Stack

### **Android App**
| Technology | Purpose |
|------------|---------|
| **Kotlin** | Primary programming language |
| **Jetpack Compose** | Modern declarative UI framework |
| **Material Design 3** | UI components and theming |
| **Room Database** | Local data persistence |
| **Kotlin Coroutines & Flow** | Asynchronous programming |
| **ViewModel** | UI state management |
| **Navigation Component** | App navigation |
| **DataStore** | Key-value storage for preferences |
| **KSP** | Kotlin Symbol Processing |

### **Backend (product-apis)**
| Technology | Purpose |
|------------|---------|
| **Ktor** | Kotlin web framework |
| **Kotlinx Serialization** | JSON serialization |
| **Logback** | Logging framework |
| **Gradle** | Build automation |

### **Development Tools**
- **Gradle 8.0+** - Build system
- **Android Studio** - IDE
- **Git** - Version control

---

## 📂 Project Structure

```
AiAgent/
├── app/                          # Main Android application module
│   ├── src/main/java/com/ai/agent/
│   │   ├── MainActivity.kt       # Entry point
│   │   ├── data/                 # Data layer
│   │   │   ├── database/         # Room database setup
│   │   │   ├── dao/              # Data Access Objects
│   │   │   ├── repository/       # Repository pattern
│   │   │   └── models/           # Data models (Product, Cart)
│   │   ├── ui/
│   │   │   ├── screen/           # Composable screens
│   │   │   │   ├── ProductListScreen.kt
│   │   │   │   ├── ProductDetailScreen.kt
│   │   │   │   └── CheckoutScreen.kt
│   │   │   ├── theme/            # Material Design 3 theme
│   │   │   └── utils/            # UI utilities & animations
│   │   ├── viewmodel/            # ViewModels
│   │   └── navigation/           # Navigation setup
│   └── src/main/res/
│       ├── values/               # English strings
│       ├── values-hi/            # Hindi translations
│       ├── values-gu/            # Gujarati translations
│       └── values-mr/            # Marathi translations
│
├── settings/                     # Reusable settings module
│   └── src/main/java/com/ai/settings/
│       ├── SettingsScreen.kt     # Settings UI
│       ├── SettingsViewModel.kt  # Settings logic
│       └── PreferencesManager.kt # DataStore wrapper
│
├── product-apis/                 # Ktor backend service
│   ├── src/main/kotlin/com/ai/agent/productapis/
│   │   ├── Application.kt        # Ktor server setup
│   │   ├── models/               # API data models
│   │   ├── repository/           # In-memory data store
│   │   ├── routes/               # API endpoints
│   │   └── plugins/              # Ktor plugins (CORS, Serialization)
│   ├── Dockerfile                # Docker configuration
│   ├── docker-compose.yml        # Docker Compose setup
│   └── README.md                 # API documentation
│
├── docs/                         # Documentation
│   ├── BOUNCE_ANIMATION_IMPLEMENTATION.md
│   ├── IMPLEMENTATION_SUMMARY.md
│   ├── MARATHI_LANGUAGE_IMPLEMENTATION.md
│   ├── MATERIAL_DESIGN_REFINEMENT.md
│   ├── PRODUCT_DETAIL_SCREEN_IMPLEMENTATION.md
│   ├── PRODUCT_DETAIL_TRANSLATIONS.md
│   ├── ROOM_DATABASE_IMPLEMENTATION.md
│   ├── SETTINGS_MODULE_DOCUMENTATION.md
│   ├── SWIPE_TO_DELETE_BUG_FIX.md
│   └── SWIPE_TO_DELETE_IMPLEMENTATION.md
│
├── gradle/                       # Gradle wrapper & dependencies
├── build.gradle.kts              # Root build configuration
├── settings.gradle.kts           # Project modules configuration
└── README.md                     # This file
```

---

## 🚀 Product APIs - Routes & Architecture

The **product-apis** module is a standalone Ktor backend service that provides RESTful APIs for product management.

### **Architecture Diagram**

```
┌─────────────────────────────────────────────────────────────────┐
│                        KTOR SERVER                               │
│                     (localhost:8080)                             │
└─────────────────────────────────────────────────────────────────┘
                              │
                              │
        ┌─────────────────────┼─────────────────────┐
        │                     │                     │
        ▼                     ▼                     ▼
┌──────────────┐    ┌──────────────┐    ┌──────────────┐
│   Plugins    │    │    Routes    │    │  Repository  │
│              │    │              │    │              │
│ • CORS       │    │ • Product    │    │ • In-Memory  │
│ • Serializer │───▶│   Routes     │───▶│   Storage    │
│ • Logging    │    │ • Health     │    │ • CRUD Ops   │
│ • CallLog    │    │   Check      │    │ • Search     │
└──────────────┘    └──────────────┘    └──────────────┘
```

### **API Routes**

#### **Base URL**
```
http://localhost:8080
```

#### **Health Check Endpoints**

| Method | Endpoint | Description | Response |
|--------|----------|-------------|----------|
| `GET` | `/` | Root endpoint | Server info & version |
| `GET` | `/health` | Health check | Status: UP |

#### **Product Endpoints**

| Method | Endpoint | Description | Query Parameters | Request Body |
|--------|----------|-------------|------------------|--------------|
| `GET` | `/api/v1/products` | Get all products | `page`, `pageSize`, `category`, `search` | - |
| `GET` | `/api/v1/products/{id}` | Get product by ID | - | - |
| `POST` | `/api/v1/products` | Create new product | - | `ProductCreateRequest` |
| `PUT` | `/api/v1/products/{id}` | Update product | - | `ProductUpdateRequest` |
| `DELETE` | `/api/v1/products/{id}` | Delete product | - | - |

### **API Flow Diagram**

```
┌──────────────┐
│ Android App  │
│  (Client)    │
└──────┬───────┘
       │
       │ HTTP Request
       │
       ▼
┌─────────────────────────────────────────────────────┐
│              KTOR SERVER (Port 8080)                │
│                                                     │
│  ┌─────────────────────────────────────────────┐  │
│  │          Routing Layer                      │  │
│  │                                             │  │
│  │  GET /api/v1/products?page=1&pageSize=10   │  │
│  └──────────────────┬──────────────────────────┘  │
│                     │                              │
│                     ▼                              │
│  ┌─────────────────────────────────────────────┐  │
│  │       ProductRoutes.kt                      │  │
│  │                                             │  │
│  │  • Parse query parameters                  │  │
│  │  • Validate input                          │  │
│  │  • Call repository methods                 │  │
│  │  • Format response                         │  │
│  └──────────────────┬──────────────────────────┘  │
│                     │                              │
│                     ▼                              │
│  ┌─────────────────────────────────────────────┐  │
│  │      ProductRepository.kt                   │  │
│  │                                             │  │
│  │  • In-memory product list                  │  │
│  │  • CRUD operations                         │  │
│  │  • Search & filter logic                   │  │
│  │  • Thread-safe operations                  │  │
│  └──────────────────┬──────────────────────────┘  │
│                     │                              │
│                     ▼                              │
│  ┌─────────────────────────────────────────────┐  │
│  │         JSON Response                       │  │
│  │                                             │  │
│  │  {                                          │  │
│  │    "success": true,                         │  │
│  │    "data": [...products],                   │  │
│  │    "page": 1,                               │  │
│  │    "totalItems": 10                         │  │
│  │  }                                          │  │
│  └─────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────┘
       │
       │ HTTP Response (JSON)
       │
       ▼
┌──────────────┐
│ Android App  │
│  Updates UI  │
└──────────────┘
```

### **Data Models**

```kotlin
// Product Model
data class Product(
    val id: Int,
    val name: String,
    val imageUrl: String,
    val rating: Double,
    val price: Double,
    val description: String,
    val category: String,
    val stock: Int
)

// API Response Wrapper
data class ApiResponse<T>(
    val success: Boolean,
    val data: T? = null,
    val message: String? = null,
    val timestamp: Long = System.currentTimeMillis()
)

// Paginated Response
data class PaginatedResponse<T>(
    val success: Boolean,
    val data: List<T>,
    val page: Int,
    val pageSize: Int,
    val totalItems: Int,
    val totalPages: Int,
    val timestamp: Long = System.currentTimeMillis()
)
```

### **Example API Usage**

```bash
# Get all products with pagination
curl "http://localhost:8080/api/v1/products?page=1&pageSize=5"

# Search products
curl "http://localhost:8080/api/v1/products?search=laptop"

# Get single product
curl "http://localhost:8080/api/v1/products/1"

# Create product
curl -X POST http://localhost:8080/api/v1/products \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Gaming Mouse",
    "imageUrl": "https://example.com/mouse.jpg",
    "rating": 4.5,
    "price": 49.99,
    "description": "High precision gaming mouse",
    "category": "Electronics",
    "stock": 100
  }'

# Update product
curl -X PUT http://localhost:8080/api/v1/products/1 \
  -H "Content-Type: application/json" \
  -d '{"price": 39.99, "stock": 50}'

# Delete product
curl -X DELETE http://localhost:8080/api/v1/products/1
```

### **Running the API Server**

```bash
# Using Gradle
./gradlew :product-apis:run

# Using Docker
cd product-apis
docker-compose up

# Using Docker directly
docker build -t product-apis .
docker run -p 8080:8080 product-apis
```

For detailed API documentation, see [product-apis/README.md](product-apis/README.md)

---

## 🚀 Getting Started

### **Prerequisites**

- **JDK 17** or higher
- **Android Studio** Hedgehog (2023.1.1) or later
- **Android SDK 28+** (minSdk: 28, targetSdk: 36)
- **Gradle 8.0+** (included via wrapper)

### **Installation**

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/AiAgent.git
   cd AiAgent
   ```

2. **Open in Android Studio**
   - Open Android Studio
   - Select "Open an Existing Project"
   - Navigate to the cloned directory
   - Wait for Gradle sync to complete

3. **Run the Android App**
   ```bash
   # Via command line
   ./gradlew :app:installDebug
   
   # Or use Android Studio's Run button (Shift+F10)
   ```

4. **Run the Backend Server** (Optional)
   ```bash
   # Start the Ktor server
   ./gradlew :product-apis:run
   
   # Server will be available at http://localhost:8080
   ```

### **Configuration**

#### **Local Properties**
Create `local.properties` file in the root directory:
```properties
sdk.dir=/path/to/your/Android/Sdk
```

#### **API Configuration** (if using remote backend)
Update the base URL in `app/src/main/java/com/ai/agent/data/repository/ProductRepository.kt`:
```kotlin
private const val BASE_URL = "http://your-server-url:8080"
```

---

## 📚 Documentation

Detailed documentation for specific features and implementations:

### **Feature Implementations**
- [🎨 Bounce Animation Implementation](docs/BOUNCE_ANIMATION_IMPLEMENTATION.md) - Interactive UI animations
- [🌍 Language & Theme Implementation](docs/IMPLEMENTATION_SUMMARY.md) - Multi-language support
- [🇮🇳 Marathi Language Support](docs/MARATHI_LANGUAGE_IMPLEMENTATION.md) - Marathi localization guide
- [🎭 Material Design Refinement](docs/MATERIAL_DESIGN_REFINEMENT.md) - Material Design 3 integration
- [🗑️ Swipe to Delete Feature](docs/SWIPE_TO_DELETE_IMPLEMENTATION.md) - Gesture-based deletion
- [🐛 Swipe to Delete Bug Fix](docs/SWIPE_TO_DELETE_BUG_FIX.md) - Bug fixes and improvements

### **Screen Implementations**
- [📱 Product Detail Screen](docs/PRODUCT_DETAIL_SCREEN_IMPLEMENTATION.md) - Detailed product view
- [🌐 Product Detail Translations](docs/PRODUCT_DETAIL_TRANSLATIONS.md) - Multi-language support

### **Architecture & Data**
- [🗄️ Room Database Implementation](docs/ROOM_DATABASE_IMPLEMENTATION.md) - Local data persistence
- [⚙️ Settings Module Documentation](docs/SETTINGS_MODULE_DOCUMENTATION.md) - Settings module architecture

### **API Documentation**
- [🔌 Product APIs Documentation](product-apis/README.md) - Complete API reference
- [⚡ Quick Start Guide](product-apis/QUICKSTART.md) - Fast API setup

---

## 📸 Screenshots

> Add screenshots of your app here

```
┌─────────────┐  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐
│  Product    │  │   Product   │  │  Shopping   │  │  Settings   │
│   List      │  │   Detail    │  │    Cart     │  │   Screen    │
│             │  │             │  │             │  │             │
└─────────────┘  └─────────────┘  └─────────────┘  └─────────────┘
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

### **Coding Standards**
- Follow [Kotlin coding conventions](https://kotlinlang.org/docs/coding-conventions.html)
- Use meaningful variable and function names
- Add comments for complex logic
- Write unit tests for new features
- Ensure all tests pass before submitting PR

---

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

---

## 👨‍💻 Author

**Zeel**
- GitHub: [@yourusername](https://github.com/yourusername)
- Project: [AiAgent](https://github.com/yourusername/AiAgent)

---

## 🙏 Acknowledgments

- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Modern UI toolkit
- [Ktor](https://ktor.io/) - Kotlin web framework
- [Material Design 3](https://m3.material.io/) - Design system
- [Room](https://developer.android.com/training/data-storage/room) - Database library
- All open-source contributors

---

## 📞 Support

If you encounter any issues or have questions:
- 🐛 [Open an Issue](https://github.com/yourusername/AiAgent/issues)
- 💬 [Start a Discussion](https://github.com/yourusername/AiAgent/discussions)
- 📧 Email: your.email@example.com

---

<div align="center">

**⭐ Star this repository if you find it helpful! ⭐**

Made with ❤️ using Kotlin & Jetpack Compose

</div>

