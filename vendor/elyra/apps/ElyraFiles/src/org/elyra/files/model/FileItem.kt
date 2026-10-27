package org.elyra.files.model

data class FileItem(
    val name: String,
    val path: String,
    val isDirectory: Boolean,
    val sizeBytes: Long = 0,
    val lastModifiedFormatted: String = "",
    val mimeType: String? = null
)
