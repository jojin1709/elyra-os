package org.elyra.privacycenter.service

import android.hardware.SensorPrivacyManager
import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

/**
 * Elyra Quick Settings Tile: Privacy Guard
 * One-tap hardware kill-switch for Camera and Microphone.
 */
class PrivacyTileService : TileService() {

    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onClick() {
        super.onClick()
        val tile = qsTile ?: return
        val isCurrentlyActive = tile.state == Tile.STATE_ACTIVE

        // Toggle sensors: If currently active (sensors on), turn them OFF (privacy ON)
        val newPrivacyState = isCurrentlyActive
        setSensorPrivacy(2, newPrivacyState) // Camera
        setSensorPrivacy(1, newPrivacyState) // Microphone

        updateTileState()
    }

    private fun updateTileState() {
        val tile = qsTile ?: return
        val cameraOff = isSensorPrivacyEnabled(2)
        val micOff = isSensorPrivacyEnabled(1)

        val isGuarded = cameraOff || micOff
        tile.state = if (isGuarded) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.label = if (isGuarded) "Sensors Blocked" else "Sensors Active"
        tile.subtitle = if (isGuarded) "Camera/Mic Muted" else "Normal Access"
        tile.updateTile()
    }

    private fun isSensorPrivacyEnabled(sensor: Int): Boolean {
        return try {
            val manager = getSystemService(SENSOR_PRIVACY_SERVICE) as? SensorPrivacyManager
            manager?.isSensorPrivacyEnabled(sensor) ?: false
        } catch (e: Exception) {
            false
        }
    }

    private fun setSensorPrivacy(sensor: Int, enablePrivacy: Boolean) {
        try {
            val manager = getSystemService(SENSOR_PRIVACY_SERVICE) as? SensorPrivacyManager
            manager?.setSensorPrivacy(sensor, enablePrivacy)
        } catch (_: Exception) {}
    }
}
