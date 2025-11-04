package com.ai.agent.navigation

sealed class NavRoutes(val route: String) {
    data object ProductList : NavRoutes("product_list")
    data object ProductDetail : NavRoutes("product_detail/{productId}") {
        fun createRoute(productId: Int) = "product_detail/$productId"
    }
    data object Checkout : NavRoutes("checkout")
    data object Settings : NavRoutes("settings")
}
