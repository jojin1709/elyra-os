package org.elyra.launcher.data

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.elyra.launcher.model.AppItem

class AppRepository(private val context: Context) {

    suspend fun getInstalledApps(): List<AppItem> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val mainIntent = Intent(Intent.ACTION_MAIN, null).apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

        val resolveInfos = pm.queryIntentActivities(mainIntent, 0)
        val ownPackageName = context.packageName

        resolveInfos
            .filter { it.activityInfo.packageName != ownPackageName }
            .map { resolveInfo ->
                val label = resolveInfo.loadLabel(pm).toString()
                val pkgName = resolveInfo.activityInfo.packageName
                val actName = resolveInfo.activityInfo.name
                val icon = resolveInfo.loadIcon(pm)
                val isSystem = (resolveInfo.activityInfo.applicationInfo.flags and ApplicationInfo.FLAG_SYSTEM) != 0

                AppItem(
                    label = label,
                    packageName = pkgName,
                    activityName = actName,
                    icon = icon,
                    isSystemApp = isSystem
                )
            }
            .sortedBy { it.label.lowercase() }
    }

    fun launchApp(app: AppItem) {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(app.packageName)
        if (launchIntent != null) {
            launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(launchIntent)
        }
    }
}
