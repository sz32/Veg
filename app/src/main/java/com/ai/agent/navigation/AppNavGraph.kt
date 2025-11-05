package com.ai.agent.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ai.agent.ui.screen.CheckoutScreen
import com.ai.agent.ui.screen.LoginScreen
import com.ai.agent.ui.screen.ProductDetailScreen
import com.ai.agent.ui.screen.ProductListScreen
import com.ai.agent.ui.screen.RegisterScreen
import com.ai.settings.ui.SettingsScreen
import com.ai.agent.viewmodel.ProductViewModel

@Composable
fun AppNavGraph(
    navController: NavHostController,
    productViewModel: ProductViewModel = viewModel()
) {
    NavHost(
        navController = navController,
        startDestination = NavRoutes.Login.route
    ) {
        composable(NavRoutes.ProductList.route) {
            ProductListScreen(
                viewModel = productViewModel,
                onCartClick = {
                    navController.navigate(NavRoutes.Checkout.route)
                },
                onSettingsClick = {
                    navController.navigate(NavRoutes.Settings.route)
                },
                onProductClick = { productId ->
                    navController.navigate(NavRoutes.ProductDetail.createRoute(productId))
                }
            )
        }

        composable(
            route = NavRoutes.ProductDetail.route,
            arguments = listOf(
                navArgument("productId") {
                    type = NavType.IntType
                }
            )
        ) { backStackEntry ->
            val productId = backStackEntry.arguments?.getInt("productId") ?: 0
            ProductDetailScreen(
                productId = productId,
                viewModel = productViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavRoutes.Checkout.route) {
            CheckoutScreen(
                viewModel = productViewModel,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavRoutes.Settings.route) {
            SettingsScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(NavRoutes.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(NavRoutes.ProductList.route) {
                        popUpTo(NavRoutes.Login.route) { inclusive = true }
                    }
                },
                onNavigateToRegister = {
                    navController.navigate(NavRoutes.Register.route)
                }
            )
        }
        composable(NavRoutes.Register.route) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(NavRoutes.Register.route) { inclusive = true }
                    }
                },
                onNavigateToLogin = {
                    navController.navigate(NavRoutes.Login.route) {
                        popUpTo(NavRoutes.Register.route) { inclusive = true }
                    }
                }
            )
        }
    }
}
