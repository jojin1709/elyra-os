package org.elyra.privacycenter.model

data class SensorAccessEvent(
    val appName: String,
    val packageName: String,
    val sensorType: String, // "CAMERA", "MICROPHONE", "LOCATION"
    val timestampFormatted: String,
    val isBackground: Boolean = false
)

data class PrivacyState(
    val isCameraEnabled: Boolean = true,
    val isMicEnabled: Boolean = true,
    val clipboardReadsLast24h: Int = 3,
    val recentSensorEvents: List<SensorAccessEvent> = emptyList()
)
