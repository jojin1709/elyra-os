package org.elyra.securitycenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.elyra.securitycenter.data.SecurityRepository
import org.elyra.securitycenter.ui.SecurityCenterScreen
import org.elyra.securitycenter.ui.SecurityCenterViewModel
import org.elyra.theme.ElyraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = SecurityRepository(applicationContext)
        val viewModel = SecurityCenterViewModel(repository)

        setContent {
            ElyraTheme {
                SecurityCenterScreen(viewModel = viewModel)
            }
        }
    }
}
