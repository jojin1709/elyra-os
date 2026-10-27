package org.elyra.privacycenter.data

import android.content.Context
import android.hardware.SensorPrivacyManager
import org.elyra.privacycenter.model.PrivacyState
import org.elyra.privacycenter.model.SensorAccessEvent

class PrivacyRepository(private val context: Context) {

    fun getPrivacyState(): PrivacyState {
        var cameraEnabled = true
        var micEnabled = true

        try {
            val sensorPrivacyManager = context.getSystemService(Context.SENSOR_PRIVACY_SERVICE) as? SensorPrivacyManager
            if (sensorPrivacyManager != null) {
                // SensorPrivacyManager.Sensors.CAMERA = 2, MICROPHONE = 1
                cameraEnabled = !sensorPrivacyManager.isSensorPrivacyEnabled(2)
                micEnabled = !sensorPrivacyManager.isSensorPrivacyEnabled(1)
            }
        } catch (e: Exception) {
            // Default to enabled if service not bound in standalone tests
            cameraEnabled = true
            micEnabled = true
        }

        val dummyEvents = listOf(
            SensorAccessEvent(
                appName = "Camera",
                packageName = "com.google.android.GoogleCamera",
                sensorType = "CAMERA",
                timestampFormatted = "12m ago",
                isBackground = false
            ),
            SensorAccessEvent(
                appName = "Phone",
                packageName = "com.android.dialer",
                sensorType = "MICROPHONE",
                timestampFormatted = "48m ago",
                isBackground = false
            ),
            SensorAccessEvent(
                appName = "Maps",
                packageName = "org.openrouteservice.maps",
                sensorType = "LOCATION",
                timestampFormatted = "2h ago",
                isBackground = true
            )
        )

        return PrivacyState(
            isCameraEnabled = cameraEnabled,
            isMicEnabled = micEnabled,
            clipboardReadsLast24h = 2,
            recentSensorEvents = dummyEvents
        )
    }

    fun setSensorEnabled(sensorType: Int, enabled: Boolean) {
        try {
            val sensorPrivacyManager = context.getSystemService(Context.SENSOR_PRIVACY_SERVICE) as? SensorPrivacyManager
            sensorPrivacyManager?.setSensorPrivacy(sensorType, !enabled)
        } catch (_: Exception) {}
    }
}
