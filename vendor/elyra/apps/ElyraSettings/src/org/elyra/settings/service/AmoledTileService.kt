package org.elyra.settings.service

import android.service.quicksettings.Tile
import android.service.quicksettings.TileService

/**
 * Elyra Quick Settings Tile: AMOLED Pure Black
 * One-tap toggle for true #000000 deep dark mode across all surfaces.
 */
class AmoledTileService : TileService() {

    private var isAmoled = true

    override fun onStartListening() {
        super.onStartListening()
        updateTileState()
    }

    override fun onClick() {
        super.onClick()
        isAmoled = !isAmoled
        updateTileState()
    }

    private fun updateTileState() {
        val tile = qsTile ?: return
        tile.state = if (isAmoled) Tile.STATE_ACTIVE else Tile.STATE_INACTIVE
        tile.label = if (isAmoled) "AMOLED Black" else "Standard Dark"
        tile.subtitle = if (isAmoled) "True #000000" else "Slate Gray"
        tile.updateTile()
    }
}
