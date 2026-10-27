package org.elyra.launcher.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.elyra.launcher.R
import org.elyra.launcher.model.AppItem
import org.elyra.theme.ElyraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDrawerSheet(
    state: LauncherUiState,
    onSearchChange: (String) -> Unit,
    onAppClick: (AppItem) -> Unit,
    onDismiss: () -> Unit
) {
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true),
        containerColor = if (state.isAmoledDrawer) Color.Black else ElyraTheme.colors.surface,
        dragHandle = {
            Box(
                modifier = Modifier
                    .padding(vertical = 12.dp)
                    .width(36.dp)
                    .height(4.dp)
                    .clip(RoundedCornerShape(9999.dp))
                    .background(ElyraTheme.colors.outline)
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            // Search Input
            OutlinedTextField(
                value = state.searchQuery,
                onValueChange = onSearchChange,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                placeholder = {
                    Text(
                        text = stringResource(R.string.search_hint),
                        fontSize = 14.sp,
                        color = ElyraTheme.colors.onSurfaceVariant
                    )
                },
                singleLine = true,
                shape = RoundedCornerShape(9999.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = ElyraTheme.colors.primary,
                    unfocusedBorderColor = ElyraTheme.colors.outline,
                    focusedContainerColor = ElyraTheme.colors.surfaceVariant,
                    unfocusedContainerColor = ElyraTheme.colors.surfaceVariant
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Apps Grid
            LazyVerticalGrid(
                columns = GridCells.Fixed(state.gridColumns),
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(state.filteredApps, key = { it.packageName }) { app ->
                    AppGridItem(app = app, onClick = { onAppClick(app) })
                }
            }
        }
    }
}

@Composable
fun AppGridItem(
    app: AppItem,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable(onClick = onClick)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterVertically
    ) {
        // App Icon Container
        Box(
            modifier = Modifier
                .size(54.dp)
                .clip(CircleShape)
                .background(ElyraTheme.colors.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = app.label.take(1).uppercase(),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = ElyraTheme.colors.primary
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = app.label,
            fontSize = 12.sp,
            fontWeight = FontWeight.Medium,
            color = ElyraTheme.colors.onSurface,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Center
        )
    }
}
