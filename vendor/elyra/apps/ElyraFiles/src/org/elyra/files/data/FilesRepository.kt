package org.elyra.files.data

import android.os.Environment
import android.os.StatFs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.elyra.files.model.FileItem
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class StorageStats(
    val totalBytes: Long,
    val availableBytes: Long,
    val usedBytes: Long,
    val usedPercentage: Int
)

class FilesRepository {

    suspend fun getStorageStats(): StorageStats = withContext(Dispatchers.IO) {
        val path = Environment.getDataDirectory()
        val stat = StatFs(path.path)
        val blockSize = stat.blockSizeLong
        val totalBlocks = stat.blockCountLong
        val availableBlocks = stat.availableBlocksLong

        val total = totalBlocks * blockSize
        val avail = availableBlocks * blockSize
        val used = total - avail
        val pct = if (total > 0) ((used * 100) / total).toInt() else 0

        StorageStats(
            totalBytes = total,
            availableBytes = avail,
            usedBytes = used,
            usedPercentage = pct
        )
    }

    suspend fun listFiles(directoryPath: String): List<FileItem> = withContext(Dispatchers.IO) {
        val dir = File(directoryPath)
        if (!dir.exists() || !dir.isDirectory) return@withContext emptyList()

        val files = dir.listFiles() ?: return@withContext emptyList()
        val dateFormat = SimpleDateFormat("MMM d, yyyy", Locale.getDefault())

        files.map { file ->
            FileItem(
                name = file.name,
                path = file.absolutePath,
                isDirectory = file.isDirectory,
                sizeBytes = if (file.isFile) file.length() else 0,
                lastModifiedFormatted = dateFormat.format(Date(file.lastModified()))
            )
        }.sortedWith(compareByDescending<FileItem> { it.isDirectory }.thenBy { it.name.lowercase() })
    }
}
