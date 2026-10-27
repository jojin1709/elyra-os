package org.elyra.updater.ui

import androidx.compose.foundation.background
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.elyra.theme.ElyraTheme
import org.elyra.updater.R
import org.elyra.updater.model.UpdateInfo
import org.elyra.updater.model.UpdateStatus

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UpdaterScreen(viewModel: UpdaterViewModel) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.updater_title),
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
                .padding(horizontal = 20.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Hero Status Overview Card
            CurrentVersionCard(state)

            // Rollback Safety Notice Card
            RollbackNoticeCard()

            // Dynamic Action / Status Card
            when (state.status) {
                UpdateStatus.IDLE, UpdateStatus.UP_TO_DATE -> {
                    UpToDateCard(onCheckClick = { viewModel.checkForUpdates() })
                }
                UpdateStatus.CHECKING -> {
                    CheckingCard()
                }
                UpdateStatus.AVAILABLE -> {
                    UpdateAvailableCard(
                        state = state,
                        onInstallClick = { viewModel.installUpdate() }
                    )
                }
                UpdateStatus.DOWNLOADING -> {
                    DownloadingCard(progress = state.downloadProgress)
                }
                UpdateStatus.INSTALLING -> {
                    InstallingCard()
                }
                UpdateStatus.RESTART_REQUIRED -> {
                    RestartRequiredCard(onRestartClick = { /* Reboot intent */ })
                }
                UpdateStatus.ERROR -> {
                    Text("Error checking updates.", color = ElyraTheme.colors.error)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun CurrentVersionCard(state: UpdateInfo) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Elyra OS ${state.currentVersion}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Android ${state.androidVersion} • Security Patch ${state.patchLevel}",
                fontSize = 13.sp,
                color = ElyraTheme.colors.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(6.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .clip(CircleShape)
                        .background(ElyraTheme.colors.success)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Active Partition: ${state.activeSlot}",
                    fontSize = 12.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun RollbackNoticeCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.primary.copy(alpha = 0.10f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Automated Rollback Protection",
                fontSize = 14.sp,
                fontWeight = FontWeight.SemiBold,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.ab_seamless_notice),
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = ElyraTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Composable
fun UpToDateCard(onCheckClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(ElyraTheme.colors.success.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "✓", fontSize = 24.sp, color = ElyraTheme.colors.success, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = stringResource(R.string.up_to_date_title),
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                color = ElyraTheme.colors.onSurface
            )
            Text(
                text = stringResource(R.string.up_to_date_desc),
                fontSize = 13.sp,
                color = ElyraTheme.colors.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(18.dp))
            Button(
                onClick = onCheckClick,
                shape = RoundedCornerShape(9999.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElyraTheme.colors.primary)
            ) {
                Text(text = stringResource(R.string.check_updates_btn), fontSize = 14.sp)
            }
        }
    }
}

@Composable
fun CheckingCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = ElyraTheme.colors.primary,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = stringResource(R.string.checking_updates),
                fontSize = 15.sp,
                color = ElyraTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun UpdateAvailableCard(state: UpdateInfo, onInstallClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Update Available: Elyra OS ${state.targetVersion ?: ""}",
                fontSize = 17.sp,
                fontWeight = FontWeight.Bold,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Changelog:",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            for (item in state.changelog) {
                Text(
                    text = "• $item",
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = onInstallClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(9999.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElyraTheme.colors.primary)
            ) {
                Text(text = stringResource(R.string.install_update_btn))
            }
        }
    }
}

@Composable
fun DownloadingCard(progress: Float) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Downloading Update payload… ${(progress * 100).toInt()}%",
                fontSize = 15.sp,
                fontWeight = FontWeight.Medium,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(10.dp))
            LinearProgressIndicator(
                progress = { progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp)
                    .clip(RoundedCornerShape(9999.dp)),
                color = ElyraTheme.colors.primary,
                trackColor = ElyraTheme.colors.outline
            )
        }
    }
}

@Composable
fun InstallingCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = ElyraTheme.colors.primary,
                strokeWidth = 3.dp
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Installing to inactive partition slot in background…",
                fontSize = 14.sp,
                color = ElyraTheme.colors.onSurface
            )
        }
    }
}

@Composable
fun RestartRequiredCard(onRestartClick: () -> Unit) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.success.copy(alpha = 0.12f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Text(
                text = "Update Installed Successfully",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Restart your device to switch partition slots and boot into the new release.",
                fontSize = 13.sp,
                color = ElyraTheme.colors.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(14.dp))
            Button(
                onClick = onRestartClick,
                shape = RoundedCornerShape(9999.dp),
                colors = ButtonDefaults.buttonColors(containerColor = ElyraTheme.colors.primary)
            ) {
                Text(text = stringResource(R.string.restart_btn))
            }
        }
    }
}
