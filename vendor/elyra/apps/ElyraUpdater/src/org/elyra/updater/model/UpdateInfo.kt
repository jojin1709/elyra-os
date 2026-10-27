package org.elyra.updater.model

enum class UpdateStatus {
    IDLE,
    CHECKING,
    UP_TO_DATE,
    AVAILABLE,
    DOWNLOADING,
    VERIFYING,
    INSTALLING,
    RESTART_REQUIRED,
    ERROR
}

data class UpdateInfo(
    val currentVersion: String = "1.0.0-alpha",
    val androidVersion: String = "15 (Vanilla Ice Cream)",
    val patchLevel: String = "2026-10-01",
    val activeSlot: String = "Slot A",
    val status: UpdateStatus = UpdateStatus.UP_TO_DATE,
    val targetVersion: String? = null,
    val downloadProgress: Float = 0f,
    val changelog: List<String> = emptyList(),
    val errorMessage: String? = null
)
