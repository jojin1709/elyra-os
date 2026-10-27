package org.elyra.settings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.elyra.settings.data.SettingsRepository
import org.elyra.settings.ui.SettingsScreen
import org.elyra.settings.ui.SettingsViewModel
import org.elyra.theme.ElyraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = SettingsRepository()
        val viewModel = SettingsViewModel(repository)

        setContent {
            ElyraTheme {
                SettingsScreen(viewModel = viewModel)
            }
        }
    }
}
