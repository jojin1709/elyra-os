package org.elyra.launcher.model

import android.graphics.drawable.Drawable

data class AppItem(
    val label: String,
    val packageName: String,
    val activityName: String,
    val icon: Drawable? = null,
    val isSystemApp: Boolean = false
)
