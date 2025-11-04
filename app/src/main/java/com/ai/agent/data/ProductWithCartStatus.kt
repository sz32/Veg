package com.ai.agent.data

data class ProductWithCartStatus(
    val product: Product,
    val isInCart: Boolean = false
)

