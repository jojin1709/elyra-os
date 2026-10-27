package org.elyra.updater

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.elyra.theme.ElyraTheme
import org.elyra.updater.data.UpdateRepository
import org.elyra.updater.ui.UpdaterScreen
import org.elyra.updater.ui.UpdaterViewModel

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = UpdateRepository()
        val viewModel = UpdaterViewModel(repository)

        setContent {
            ElyraTheme {
                UpdaterScreen(viewModel = viewModel)
            }
        }
    }
}
