package org.elyra.securitycenter.ui

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
import org.elyra.securitycenter.R
import org.elyra.securitycenter.model.BootStateGrade
import org.elyra.securitycenter.model.SecurityState
import org.elyra.theme.ElyraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SecurityCenterScreen(viewModel: SecurityCenterViewModel) {
    val state by viewModel.uiState.collectAsState()
    val scrollState = rememberScrollState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.security_overview),
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
            HeroSecurityCard(state)

            // Freedom Layer Banner (Shown if custom key or unlocked)
            if (state.bootStateGrade != BootStateGrade.SECURE_GREEN) {
                FreedomStateBanner(state)
            }

            Text(
                text = "Integrity Metrics",
                fontSize = 17.sp,
                fontWeight = FontWeight.Medium,
                color = ElyraTheme.colors.onSurface
            )

            // Metrics Cards Group
            SecurityMetricCard(
                title = stringResource(R.string.avb_title),
                subtitle = when (state.bootStateGrade) {
                    BootStateGrade.SECURE_GREEN -> stringResource(R.string.avb_desc_locked)
                    BootStateGrade.CUSTOM_YELLOW -> stringResource(R.string.avb_desc_yellow)
                    BootStateGrade.UNLOCKED_ORANGE -> stringResource(R.string.avb_desc_unlocked)
                    BootStateGrade.TAMPERED_RED -> "Verification failed."
                },
                badgeText = state.verifiedBootStateString.uppercase(),
                badgeColor = when (state.bootStateGrade) {
                    BootStateGrade.SECURE_GREEN -> ElyraTheme.colors.success
                    BootStateGrade.CUSTOM_YELLOW -> ElyraTheme.colors.warning
                    BootStateGrade.UNLOCKED_ORANGE -> ElyraTheme.colors.warning
                    BootStateGrade.TAMPERED_RED -> ElyraTheme.colors.error
                }
            )

            SecurityMetricCard(
                title = stringResource(R.string.selinux_title),
                subtitle = if (state.isSELinuxEnforcing) stringResource(R.string.selinux_enforcing) else stringResource(R.string.selinux_permissive),
                badgeText = if (state.isSELinuxEnforcing) "ENFORCING" else "PERMISSIVE",
                badgeColor = if (state.isSELinuxEnforcing) ElyraTheme.colors.success else ElyraTheme.colors.error
            )

            SecurityMetricCard(
                title = stringResource(R.string.encryption_title),
                subtitle = stringResource(R.string.encryption_fbe),
                badgeText = "FBE ENCRYPTED",
                badgeColor = ElyraTheme.colors.success
            )

            SecurityMetricCard(
                title = stringResource(R.string.patch_level_title),
                subtitle = "Elyra Build: ${state.buildId}",
                badgeText = state.securityPatchLevel,
                badgeColor = ElyraTheme.colors.primary
            )

            SecurityMetricCard(
                title = stringResource(R.string.root_status_title),
                subtitle = if (state.isRootDetected) stringResource(R.string.root_detected) else stringResource(R.string.root_not_detected),
                badgeText = if (state.isRootDetected) "ROOT ACTIVE" else "NOT INSTALLED",
                badgeColor = if (state.isRootDetected) ElyraTheme.colors.warning else ElyraTheme.colors.success
            )

            SecurityMetricCard(
                title = stringResource(R.string.adb_status_title),
                subtitle = if (state.isAdbEnabled) stringResource(R.string.adb_active) else stringResource(R.string.adb_inactive),
                badgeText = if (state.isAdbEnabled) "DEBUG ON" else "OFF",
                badgeColor = if (state.isAdbEnabled) ElyraTheme.colors.warning else ElyraTheme.colors.secondary
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@Composable
fun HeroSecurityCard(state: SecurityState) {
    val gradeColor = when (state.bootStateGrade) {
        BootStateGrade.SECURE_GREEN -> ElyraTheme.colors.success
        BootStateGrade.CUSTOM_YELLOW -> ElyraTheme.colors.warning
        BootStateGrade.UNLOCKED_ORANGE -> ElyraTheme.colors.warning
        BootStateGrade.TAMPERED_RED -> ElyraTheme.colors.error
    }

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
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(gradeColor.copy(alpha = 0.15f)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .clip(CircleShape)
                        .background(gradeColor)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = when (state.bootStateGrade) {
                        BootStateGrade.SECURE_GREEN -> stringResource(R.string.status_secure)
                        BootStateGrade.CUSTOM_YELLOW -> "Custom Root of Trust Active"
                        BootStateGrade.UNLOCKED_ORANGE -> stringResource(R.string.status_warning)
                        BootStateGrade.TAMPERED_RED -> stringResource(R.string.status_compromised)
                    },
                    fontSize = 17.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ElyraTheme.colors.onSurface
                )
                Text(
                    text = "SELinux Enforcing • Hardware Keystore Active",
                    fontSize = 13.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
fun FreedomStateBanner(state: SecurityState) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.warning.copy(alpha = 0.12f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = stringResource(R.string.unlocked_banner_title),
                fontWeight = FontWeight.SemiBold,
                fontSize = 15.sp,
                color = ElyraTheme.colors.onSurface
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = stringResource(R.string.unlocked_banner_desc),
                fontSize = 13.sp,
                lineHeight = 18.sp,
                color = ElyraTheme.colors.onSurfaceVariant
            )
        }
    }
}

@Composable
fun SecurityMetricCard(
    title: String,
    subtitle: String,
    badgeText: String,
    badgeColor: Color
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
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(9999.dp))
                    .background(badgeColor.copy(alpha = 0.15f))
                    .padding(horizontal = 10.dp, vertical = 4.dp)
            ) {
                Text(
                    text = badgeText,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = badgeColor
                )
            }
        }
    }
}
