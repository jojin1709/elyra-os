package org.elyra.privacycenter

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import org.elyra.privacycenter.data.PrivacyRepository
import org.elyra.privacycenter.ui.PrivacyCenterScreen
import org.elyra.privacycenter.ui.PrivacyCenterViewModel
import org.elyra.theme.ElyraTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = PrivacyRepository(applicationContext)
        val viewModel = PrivacyCenterViewModel(repository)

        setContent {
            ElyraTheme {
                PrivacyCenterScreen(viewModel = viewModel)
            }
        }
    }
}
