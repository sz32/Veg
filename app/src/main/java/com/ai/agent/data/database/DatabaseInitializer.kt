package com.ai.agent.data.database

import android.content.Context
import com.ai.agent.data.DummyProductData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseInitializer(private val context: Context) {

    fun initializeDatabase() {
        CoroutineScope(Dispatchers.IO).launch {
            val database = AppDatabase.getDatabase(context)
            val productDao = database.productDao()

            // Check if database is already populated
            val productCount = productDao.getProductCount()

            if (productCount == 0) {
                // Insert dummy data
                val products = DummyProductData.getProducts()
                productDao.insertAllProducts(products)
            }
        }
    }
}

