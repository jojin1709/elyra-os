package org.elyra.privacycenter.ui

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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.elyra.privacycenter.R
import org.elyra.privacycenter.model.PrivacyState
import org.elyra.privacycenter.model.SensorAccessEvent
import org.elyra.theme.ElyraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrivacyCenterScreen(viewModel: PrivacyCenterViewModel) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.privacy_title),
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
            // Zero Telemetry Invariant Card
            ZeroTelemetryCard()

            Text(
                text = stringResource(R.string.hardware_killswitches),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = ElyraTheme.colors.onSurface
            )

            // Camera Sensor Kill Switch Card
            SensorKillSwitchCard(
                title = stringResource(R.string.camera_killswitch_title),
                subtitle = stringResource(R.string.camera_killswitch_desc),
                isEnabled = state.isCameraEnabled,
                onCheckedChange = { viewModel.toggleCamera(it) }
            )

            // Mic Sensor Kill Switch Card
            SensorKillSwitchCard(
                title = stringResource(R.string.mic_killswitch_title),
                subtitle = stringResource(R.string.mic_killswitch_desc),
                isEnabled = state.isMicEnabled,
                onCheckedChange = { viewModel.toggleMicrophone(it) }
            )

            // Clipboard Auditor Card
            ClipboardAuditorCard(state.clipboardReadsLast24h)

            Text(
                text = stringResource(R.string.recent_sensor_activity),
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = ElyraTheme.colors.onSurface
            )

            // Recent Sensor Events Timeline
            for (event in state.recentSensorEvents) {
                SensorEventCard(event)
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun ZeroTelemetryCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.primary.copy(alpha = 0.10f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.zero_telemetry_title),
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.zero_telemetry_desc),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = ElyraTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SensorKillSwitchCard(
    title: String,
    subtitle: String,
    isEnabled: Boolean,
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
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = subtitle,
                    fontSize = 13.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Switch(
                checked = isEnabled,
                onCheckedChange = onCheckedChange,
                colors = SwitchDefaults.colors(
                    checkedThumbColor = ElyraTheme.colors.onPrimary,
                    checkedTrackColor = ElyraTheme.colors.primary,
                    uncheckedThumbColor = ElyraTheme.colors.onSurfaceVariant,
                    uncheckedTrackColor = ElyraTheme.colors.outline
                )
            )
        }
    }
}

@Composable
fun ClipboardAuditorCard(count: Int) {
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
                    text = stringResource(R.string.clipboard_audit_title),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = ElyraTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(R.string.clipboard_audit_desc),
                    fontSize = 13.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .clip(CircleShape)
                    .background(ElyraTheme.colors.primary.copy(alpha = 0.15f))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "$count reads",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ElyraTheme.colors.primary
                )
            }
        }
    }
}

@Composable
fun SensorEventCard(event: SensorAccessEvent) {
    val sensorColor = when (event.sensorType) {
        "CAMERA" -> ElyraTheme.colors.error
        "MICROPHONE" -> ElyraTheme.colors.warning
        else -> ElyraTheme.colors.primary
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(sensorColor)
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = event.appName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = ElyraTheme.colors.onSurface
                    )
                    Text(
                        text = "${event.sensorType} • ${event.timestampFormatted}",
                        fontSize = 12.sp,
                        color = ElyraTheme.colors.onSurfaceVariant
                    )
                }
            }
            if (event.isBackground) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .background(ElyraTheme.colors.warning.copy(alpha = 0.15f))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = "BACKGROUND",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = ElyraTheme.colors.warning
                    )
                }
            }
        }
    }
}
