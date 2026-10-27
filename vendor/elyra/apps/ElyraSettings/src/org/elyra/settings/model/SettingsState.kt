package org.elyra.settings.model

data class SettingsState(
    val isAmoledEnabled: Boolean = true,
    val selectedAccentName: String = "Calm Indigo",
    val deviceModel: String = "Google Pixel 7 (panther)",
    val androidVersion: String = "15 (API 35)",
    val elyraVersion: String = "1.0.0-alpha",
    val kernelVersion: String = "Linux 6.1.110-gki",
    val buildNumber: String = "ELYRA-15.0-panther-userdebug"
)
