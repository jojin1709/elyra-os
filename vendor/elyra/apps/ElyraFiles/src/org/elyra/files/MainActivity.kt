package org.elyra.files

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.elyra.files.data.FilesRepository
import org.elyra.files.ui.FilesScreen
import org.elyra.files.ui.FilesViewModel
import org.elyra.theme.ElyraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = FilesRepository()
        val viewModel = FilesViewModel(repository)

        setContent {
            ElyraTheme {
                FilesScreen(viewModel = viewModel)
            }
        }
    }
}
