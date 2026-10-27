package org.elyra.files.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.elyra.files.R
import org.elyra.files.model.FileItem
import org.elyra.theme.ElyraTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilesScreen(viewModel: FilesViewModel) {
    val state by viewModel.uiState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.app_name),
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
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Spacer(modifier = Modifier.height(4.dp))
                // Storage Usage Card
                StorageUsageCard(
                    usedPct = state.storageStats.usedPercentage,
                    usedGb = state.storageStats.usedBytes / (1024 * 1024 * 1024),
                    totalGb = state.storageStats.totalBytes / (1024 * 1024 * 1024)
                )
            }

            item {
                // Secure Vault Card
                SecureVaultCard()
            }

            item {
                Text(
                    text = stringResource(R.string.categories_title),
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = ElyraTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(10.dp))
                // Categories Chips Row
                CategoryChipsRow()
            }

            item {
                Text(
                    text = "Storage Explorer",
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Medium,
                    color = ElyraTheme.colors.onSurface
                )
            }

            if (state.items.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = stringResource(R.string.empty_folder),
                            color = ElyraTheme.colors.onSurfaceVariant,
                            fontSize = 14.sp
                        )
                    }
                }
            } else {
                items(state.items, key = { it.path }) { item ->
                    FileRowItem(
                        item = item,
                        onClick = {
                            if (item.isDirectory) {
                                viewModel.navigateTo(item.path)
                            }
                        }
                    )
                }
            }

            item {
                Spacer(modifier = Modifier.height(24.dp))
            }
        }
    }
}

@Composable
fun StorageUsageCard(usedPct: Int, usedGb: Long, totalGb: Long) {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(18.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.storage_overview),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.Medium,
                    color = ElyraTheme.colors.onSurface
                )
                Text(
                    text = "$usedGb GB / $totalGb GB",
                    fontSize = 13.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
            Spacer(modifier = Modifier.height(12.dp))
            LinearProgressIndicator(
                progress = { (usedPct / 100f).coerceIn(0f, 1f) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(9999.dp)),
                color = ElyraTheme.colors.primary,
                trackColor = ElyraTheme.colors.outline
            )
        }
    }
}

@Composable
fun SecureVaultCard() {
    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.primary.copy(alpha = 0.12f)),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = stringResource(R.string.cat_vault),
                    fontSize = 15.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = ElyraTheme.colors.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = stringResource(R.string.vault_desc),
                    fontSize = 13.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(9999.dp))
                    .background(ElyraTheme.colors.primary)
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Text(
                    text = "LOCKED",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
fun CategoryChipsRow() {
    val categories = listOf("Images", "Videos", "Audio", "Documents", "Downloads")
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(horizontal = 2.dp)
    ) {
        items(categories) { cat ->
            Surface(
                shape = RoundedCornerShape(9999.dp),
                color = ElyraTheme.colors.surfaceVariant,
                modifier = Modifier.clickable {}
            ) {
                Text(
                    text = cat,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Medium,
                    color = ElyraTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
fun FileRowItem(
    item: FileItem,
    onClick: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = ElyraTheme.colors.surfaceVariant),
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(
                        if (item.isDirectory) ElyraTheme.colors.primary.copy(alpha = 0.15f)
                        else ElyraTheme.colors.outline.copy(alpha = 0.5f)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = if (item.isDirectory) "📁" else "📄",
                    fontSize = 16.sp
                )
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.name,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = ElyraTheme.colors.onSurface,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = if (item.isDirectory) item.lastModifiedFormatted
                    else "${formatBytes(item.sizeBytes)} • ${item.lastModifiedFormatted}",
                    fontSize = 12.sp,
                    color = ElyraTheme.colors.onSurfaceVariant
                )
            }
        }
    }
}

fun formatBytes(bytes: Long): String {
    if (bytes < 1024) return "$bytes B"
    val kb = bytes / 1024.0
    if (kb < 1024) return "%.1f KB".format(kb)
    val mb = kb / 1024.0
    if (mb < 1024) return "%.1f MB".format(mb)
    val gb = mb / 1024.0
    return "%.2f GB".format(gb)
}
