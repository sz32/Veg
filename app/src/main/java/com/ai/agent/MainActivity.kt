package com.ai.agent

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.ai.settings.data.PreferencesManager
import com.ai.agent.navigation.AppNavGraph
import com.ai.agent.ui.theme.AiAgentTheme
import java.util.Locale

class MainActivity : ComponentActivity() {
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        preferencesManager = PreferencesManager(this)
        enableEdgeToEdge()
        setContent {
            val language by preferencesManager.languageFlow.collectAsState(initial = "en")
            val theme by preferencesManager.themeFlow.collectAsState(initial = "system")

            // Apply language
            applyLanguage(language)

            // Determine dark theme based on preference
            val darkTheme = when (theme) {
                "light" -> false
                "dark" -> true
                else -> isSystemInDarkTheme()
            }

            AiAgentTheme(darkTheme = darkTheme) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    // Start at Login screen if not authenticated
                    AppNavGraph(navController = navController)
                }
            }
        }
    }

    private fun applyLanguage(languageCode: String) {
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(resources.configuration)
        config.setLocale(locale)
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    override fun attachBaseContext(newBase: Context) {
        val preferencesManager = PreferencesManager(newBase)
        // This is a workaround since we can't use Flow here
        // We'll use the default language from shared preferences
        val sharedPrefs = newBase.getSharedPreferences("settings", Context.MODE_PRIVATE)
        val languageCode = sharedPrefs.getString("language", "en") ?: "en"

        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = Configuration(newBase.resources.configuration)
        config.setLocale(locale)

        super.attachBaseContext(newBase.createConfigurationContext(config))
    }
}
