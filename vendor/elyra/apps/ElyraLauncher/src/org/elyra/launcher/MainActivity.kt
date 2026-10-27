package org.elyra.launcher

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.elyra.launcher.data.AppRepository
import org.elyra.launcher.ui.HomeScreen
import org.elyra.launcher.ui.LauncherViewModel
import org.elyra.theme.ElyraTheme

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: LauncherViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = AppRepository(applicationContext)
        viewModel = LauncherViewModel(repository)

        setContent {
            ElyraTheme(amoled = true) {
                HomeScreen(viewModel = viewModel)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (::viewModel.isInitialized) {
            viewModel.loadApps()
        }
    }
}
