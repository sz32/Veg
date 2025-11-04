package com.ai.agent.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey
    val id: Int,
    val name: String,
    val imageUrl: String,
    val rating: Float,
    val price: Double
)

