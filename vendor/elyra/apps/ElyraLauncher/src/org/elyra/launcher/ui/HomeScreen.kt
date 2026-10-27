package org.elyra.launcher.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.elyra.launcher.model.AppItem
import org.elyra.theme.ElyraTheme
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeScreen(viewModel: LauncherViewModel) {
    val state by viewModel.uiState.collectAsState()

    val timeFormat = SimpleDateFormat("h:mm", Locale.getDefault())
    val dateFormat = SimpleDateFormat("EEEE, MMMM d", Locale.getDefault())
    val now = Date()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectVerticalDragGestures { _, dragAmount ->
                    if (dragAmount < -20f) {
                        viewModel.setAppDrawerOpen(true)
                    }
                }
            }
            .padding(horizontal = 24.dp, vertical = 40.dp)
    ) {
        // Top Clock & Date Widget
        Column(
            modifier = Modifier
                .align(Alignment.TopStart)
                .padding(top = 28.dp)
        ) {
            Text(
                text = timeFormat.format(now),
                fontSize = 56.sp,
                fontWeight = FontWeight.Light,
                color = Color.White,
                letterSpacing = (-1.5).sp
            )
            Text(
                text = dateFormat.format(now),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.White.copy(alpha = 0.85f)
            )
        }

        // Bottom Dock & Search Bar Area
        Column(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Favorite Apps Dock (4 apps)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                for (app in state.dockApps) {
                    DockAppItem(app = app, onClick = { viewModel.launchApp(app) })
                }
            }

            // Search Bar Pill (Opens App Drawer)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
                    .clip(RoundedCornerShape(9999.dp))
                    .background(Color.Black.copy(alpha = 0.45f))
                    .clickable { viewModel.setAppDrawerOpen(true) }
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Search apps or swipe up…",
                        fontSize = 14.sp,
                        color = Color.White.copy(alpha = 0.70f)
                    )
                }
            }
        }

        // Full App Drawer Sheet Modal
        if (state.isAppDrawerOpen) {
            AppDrawerSheet(
                state = state,
                onSearchChange = { viewModel.updateSearchQuery(it) },
                onAppClick = { viewModel.launchApp(it) },
                onDismiss = { viewModel.setAppDrawerOpen(false) }
            )
        }
    }
}

@Composable
fun DockAppItem(
    app: AppItem,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .size(56.dp)
            .clip(CircleShape)
            .background(Color.Black.copy(alpha = 0.35f))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = app.label.take(1).uppercase(),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}
