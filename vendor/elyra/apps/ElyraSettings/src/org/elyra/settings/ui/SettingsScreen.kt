package org.elyra.settings.ui

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.elyra.settings.R
import org.elyra.settings.model.SettingsState
import org.elyra.theme.ElyraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.settings_title),
                        fontWeight = FontWeight.Medium,
                        fontSize = 22.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = ElyraTheme.colors.surface,
                    titleContentColor = ElyraTheme.colors.onSurface
                )
            )
        },
        containerColor = ElyraTheme.colors.surface
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(scrollState)
                .padding(horizontal = 20.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Elyra Experience Group
            SettingsGroupHeader(stringResource(R.string.group_elyra_experience))

            SettingsToggleCard(
                title = stringResource(R.string.amoled_theme_title),
                subtitle = stringResource(R.string.amoled_theme_desc),
                checked = state.isAmoledEnabled,
                onCheckedChange = { viewModel.toggleAmoled(it) }
            )

            SettingsNavCard(
                title = stringResource(R.string.accent_color_title),
                subtitle = stringResource(R.string.accent_color_desc),
                iconEmoji = "🎨",
                onClick = {}
            )

            // Trust & Integrity Group
            SettingsGroupHeader(stringResource(R.string.group_trust))

            SettingsNavCard(
                title = stringResource(R.string.privacy_center_title),
                subtitle = stringResource(R.string.privacy_center_desc),
                iconEmoji = "🛡️",
                onClick = { launchAction(context, "org.elyra.intent.action.PRIVACY_CENTER") }
            )

            SettingsNavCard(
                title = stringResource(R.string.security_center_title),
                subtitle = stringResource(R.string.security_center_desc),
                iconEmoji = "🔒",
                onClick = { launchAction(context, "org.elyra.intent.action.SECURITY_CENTER") }
            )

            // Device & Updates Group
            SettingsGroupHeader(stringResource(R.string.group_system))

            SettingsNavCard(
                title = stringResource(R.string.system_update_title),
                subtitle = stringResource(R.string.system_update_desc),
                iconEmoji = "⚡",
                onClick = { launchAction(context, "android.settings.SYSTEM_UPDATE_SETTINGS") }
            )

            AboutPhoneCard(state = state)

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun SettingsGroupHeader(title: String) {
    Text(
        text = title,
        fontSize = 14.sp,
        fontWeight = FontWeight.SemiBold,
        color = ElyraTheme.colors.primary,
        modifier = Modifier.padding(top = 8.dp, bottom = 2.dp)
    )
}

@Composable
fun SettingsToggleCard(
    title: String,
    subtitle: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = ElyraTheme.colors.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = ElyraTheme.colors.onPrimary,
                    checkedTrackColor = ElyraTheme.colors.primary
                )
            )
        }
    }
}

@Composable
fun SettingsNavCard(
    title: String,
    subtitle: String,
    iconEmoji: String,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(ElyraTheme.colors.primary.copy(alpha = 0.12f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = iconEmoji, fontSize = 18.sp)
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = ElyraTheme.colors.onSurface
                )
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }

            Text(
                text = "›",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ElyraTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Composable
fun AboutPhoneCard(state: SettingsState) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Device Information",
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            InfoRow("Model", state.deviceModel)
            InfoRow("OS Version", "Elyra OS ${state.elyraVersion}")
            InfoRow("Android Version", state.androidVersion)
            InfoRow("Kernel", state.kernelVersion)
            InfoRow("Build", state.buildNumber)
        }
    }
}

@Composable
fun InfoRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = label, fontSize = 13.sp, color = ElyraTheme.colors.onSurfaceVariant)
        Text(text = value, fontSize = 13.sp, fontWeight = FontWeight.Medium, color = ElyraTheme.colors.onSurface)
    }
}

fun launchAction(context: Context, action: String) {
    try {
        val intent = Intent(action).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        context.startActivity(intent)
    } catch (_: Exception) {}
}
