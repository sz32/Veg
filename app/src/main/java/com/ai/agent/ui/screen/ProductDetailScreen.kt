package com.ai.agent.ui.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.ai.agent.R
import com.ai.agent.data.Product
import com.ai.agent.data.ProductWithCartStatus
import com.ai.agent.ui.theme.AiAgentTheme
import com.ai.agent.ui.utils.bounceClick
import com.ai.agent.viewmodel.ProductViewModel
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    viewModel: ProductViewModel = viewModel(),
    onNavigateBack: () -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsState()
    val productWithStatus = uiState.products.find { it.product.id == productId }

    if (productWithStatus == null) {
        // Product not found
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.product_not_found),
                    style = MaterialTheme.typography.headlineSmall
                )
                Button(onClick = onNavigateBack) {
                    Text(stringResource(R.string.go_back))
                }
            }
        }
        return
    }

    val product = productWithStatus.product
    val isInCart = productWithStatus.isInCart

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.product_details),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Medium
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            ProductDetailBottomBar(
                price = product.price,
                isInCart = isInCart,
                onAddToCart = { viewModel.addToCart(productId) },
                onRemoveFromCart = { viewModel.removeFromCart(productId) }
            )
        },
        containerColor = MaterialTheme.colorScheme.surfaceContainerLowest
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
        ) {
            // Product Image
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(400.dp)
            ) {
                AsyncImage(
                    model = product.imageUrl,
                    contentDescription = product.name,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Crop
                )

                // Gradient overlay at bottom
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .align(Alignment.BottomCenter)
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surfaceContainerLowest
                                )
                            )
                        )
                )
            }

            // Product Details Section
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .offset(y = (-30).dp)
            ) {
                // Product Name Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = product.name,
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        // Rating and Price Row
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Rating
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Star,
                                    contentDescription = "Rating",
                                    tint = Color(0xFFFFA000),
                                    modifier = Modifier.size(24.dp)
                                )
                                Text(
                                    text = String.format(Locale.US, "%.1f", product.rating),
                                    style = MaterialTheme.typography.titleLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = "(${(product.rating * 234).toInt()} reviews)",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        HorizontalDivider(modifier = Modifier.padding(vertical = 4.dp))

                        // Price
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.price_label),
                                style = MaterialTheme.typography.titleMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text = "$${String.format(Locale.US, "%.2f", product.price)}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary,
                                fontSize = 32.sp
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Description Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.description),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = getProductDescription(product),
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            lineHeight = 24.sp
                        )
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Features Card
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceContainerHigh
                    )
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            text = stringResource(R.string.key_features),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        getProductFeatures(product).forEach { feature ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.Top
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.primary,
                                    modifier = Modifier.size(20.dp)
                                )
                                Text(
                                    text = feature,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))
            }
        }
    }
}

@Composable
fun ProductDetailBottomBar(
    price: Double,
    isInCart: Boolean,
    onAddToCart: () -> Unit,
    onRemoveFromCart: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier.fillMaxWidth(),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.surface
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Price
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = stringResource(R.string.total_price),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(
                    text = "$${String.format(Locale.US, "%.2f", price)}",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // Add to Cart Button
            AnimatedVisibility(
                visible = !isInCart,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    onClick = onAddToCart,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .bounceClick(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.add_to_cart),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Remove from Cart Button
            AnimatedVisibility(
                visible = isInCart,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Button(
                    onClick = onRemoveFromCart,
                    modifier = Modifier
                        .weight(1f)
                        .height(56.dp)
                        .bounceClick(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF4CAF50)
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = stringResource(R.string.added_to_cart),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

// Helper function to generate product description
private fun getProductDescription(product: Product): String {
    return when {
        product.name.contains("Headphones", ignoreCase = true) ||
        product.name.contains("Headset", ignoreCase = true) ||
        product.name.contains("Earbuds", ignoreCase = true) ->
            "Experience premium audio quality with these exceptional ${product.name}. Featuring advanced noise cancellation technology and crystal-clear sound reproduction, these are perfect for music lovers and professionals alike. Comfortable design ensures all-day wearability."

        product.name.contains("Watch", ignoreCase = true) ||
        product.name.contains("Tracker", ignoreCase = true) ->
            "Stay connected and track your fitness goals with this ${product.name}. Monitor your heart rate, steps, calories burned, and sleep patterns. Water-resistant design with long battery life makes it perfect for active lifestyles."

        product.name.contains("Keyboard", ignoreCase = true) ->
            "Elevate your typing experience with this ${product.name}. Featuring responsive mechanical switches, customizable RGB lighting, and durable construction. Perfect for gaming, programming, or everyday productivity tasks."

        product.name.contains("Mouse", ignoreCase = true) ->
            "Precision meets comfort with this ${product.name}. Ergonomic design reduces wrist strain during extended use. High-precision sensor and customizable buttons make it ideal for both work and gaming."

        product.name.contains("Monitor", ignoreCase = true) ->
            "Immerse yourself in stunning visuals with this ${product.name}. High resolution display with excellent color accuracy and fast response time. Perfect for professionals, gamers, and content creators."

        product.name.contains("Speaker", ignoreCase = true) ->
            "Fill your space with rich, powerful audio using this ${product.name}. Portable design with impressive battery life. Connect wirelessly via Bluetooth for seamless audio streaming from any device."

        product.name.contains("Charger", ignoreCase = true) ->
            "Never run out of power with this ${product.name}. High-capacity battery with fast charging technology. Compact and portable design makes it perfect for travel and daily use."

        product.name.contains("Stand", ignoreCase = true) ->
            "Improve your workspace ergonomics with this ${product.name}. Adjustable height and angle ensure optimal viewing position. Sturdy construction provides stable support for your device."

        product.name.contains("Hub", ignoreCase = true) ->
            "Expand your connectivity options with this ${product.name}. Multiple ports provide versatile connection possibilities. Compact design and plug-and-play functionality make it easy to use."

        product.name.contains("Tablet", ignoreCase = true) ->
            "Versatile and powerful, this ${product.name} offers the perfect balance between portability and performance. Stunning display, long battery life, and powerful processor handle all your tasks with ease."

        product.name.contains("Chair", ignoreCase = true) ->
            "Experience ultimate comfort with this ${product.name}. Ergonomic design supports proper posture during long work sessions. Adjustable features allow you to customize for perfect fit."

        product.name.contains("Lamp", ignoreCase = true) ->
            "Illuminate your workspace with this ${product.name}. Adjustable brightness levels and color temperature protect your eyes during extended use. Modern design complements any desk setup."

        product.name.contains("SSD", ignoreCase = true) ->
            "Boost your storage capacity with this ${product.name}. Fast read/write speeds ensure quick file transfers and smooth performance. Reliable and durable for long-term data storage."

        else ->
            "Discover the exceptional quality of ${product.name}. Crafted with premium materials and cutting-edge technology, this product delivers outstanding performance and reliability. Perfect for everyday use with features designed to enhance your productivity and lifestyle."
    }
}

// Helper function to generate product features
private fun getProductFeatures(product: Product): List<String> {
    return when {
        product.name.contains("Headphones", ignoreCase = true) ||
        product.name.contains("Headset", ignoreCase = true) ->
            listOf(
                "Active Noise Cancellation",
                "40-hour battery life",
                "Premium sound quality",
                "Comfortable over-ear design",
                "Built-in microphone for calls"
            )

        product.name.contains("Earbuds", ignoreCase = true) ->
            listOf(
                "True wireless design",
                "IPX4 water resistance",
                "25-hour total battery life",
                "Touch controls",
                "Premium audio drivers"
            )

        product.name.contains("Watch", ignoreCase = true) ->
            listOf(
                "Heart rate monitoring",
                "GPS tracking",
                "7-day battery life",
                "Water resistant up to 50m",
                "Sleep tracking"
            )

        product.name.contains("Tracker", ignoreCase = true) ->
            listOf(
                "24/7 activity tracking",
                "Sleep quality analysis",
                "10-day battery life",
                "Water resistant",
                "Smartphone notifications"
            )

        product.name.contains("Keyboard", ignoreCase = true) ->
            listOf(
                "Mechanical switches",
                "RGB backlighting",
                "Programmable keys",
                "Durable construction",
                "Anti-ghosting technology"
            )

        product.name.contains("Mouse", ignoreCase = true) ->
            listOf(
                "Ergonomic design",
                "High-precision sensor",
                "Customizable buttons",
                "Long battery life",
                "Universal compatibility"
            )

        product.name.contains("Monitor", ignoreCase = true) ->
            listOf(
                "4K Ultra HD resolution",
                "IPS panel technology",
                "75Hz refresh rate",
                "HDR support",
                "Multiple connectivity options"
            )

        product.name.contains("Speaker", ignoreCase = true) ->
            listOf(
                "360-degree sound",
                "12-hour battery life",
                "Bluetooth 5.0",
                "Water resistant",
                "Built-in microphone"
            )

        product.name.contains("Webcam", ignoreCase = true) ->
            listOf(
                "Full HD 1080p video",
                "Auto-focus technology",
                "Built-in microphone",
                "Low-light correction",
                "Universal clip mount"
            )

        product.name.contains("Charger", ignoreCase = true) ->
            listOf(
                "20000mAh capacity",
                "Fast charging support",
                "Multiple device charging",
                "LED battery indicator",
                "Compact and portable"
            )

        else ->
            listOf(
                "Premium quality materials",
                "Durable construction",
                "Modern design",
                "Easy to use",
                "Excellent value for money"
            )
    }
}

@Preview(showBackground = true, name = "Product Detail Screen Preview")
@Composable
fun ProductDetailScreenPreview() {
    AiAgentTheme {
        ProductDetailBottomBar(
            price = 99.99,
            isInCart = false,
            onAddToCart = {},
            onRemoveFromCart = {}
        )
    }
}

