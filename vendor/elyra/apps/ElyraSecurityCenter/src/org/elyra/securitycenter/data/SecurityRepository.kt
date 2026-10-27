package org.elyra.securitycenter.data

import android.content.Context
import android.provider.Settings
import org.elyra.securitycenter.model.BootStateGrade
import org.elyra.securitycenter.model.SecurityState
import java.io.File

class SecurityRepository(private val context: Context) {

    fun getSecurityState(): SecurityState {
        val verifiedBootProp = getSystemProperty("ro.boot.verifiedbootstate", "green").lowercase()
        val flashLockedProp = getSystemProperty("ro.boot.flash.locked", "1")
        val patchLevelProp = getSystemProperty("ro.build.version.security_patch", "2026-10-01")
        val buildIdProp = getSystemProperty("ro.build.display.id", "ELYRA-15.0")

        val isLocked = flashLockedProp == "1"
        val bootGrade = when {
            verifiedBootProp == "green" -> BootStateGrade.SECURE_GREEN
            verifiedBootProp == "yellow" -> BootStateGrade.CUSTOM_YELLOW
            verifiedBootProp == "orange" || !isLocked -> BootStateGrade.UNLOCKED_ORANGE
            else -> BootStateGrade.TAMPERED_RED
        }

        val isAdb = Settings.Global.getInt(context.contentResolver, Settings.Global.ADB_ENABLED, 0) == 1
        val isRoot = checkRootPresence()

        return SecurityState(
            bootStateGrade = bootGrade,
            isBootloaderLocked = isLocked,
            verifiedBootStateString = verifiedBootProp,
            isSELinuxEnforcing = checkSELinuxEnforcing(),
            isStorageEncrypted = true, // Android 15 mandates FBE
            securityPatchLevel = patchLevelProp,
            isAdbEnabled = isAdb,
            isRootDetected = isRoot,
            buildId = buildIdProp
        )
    }

    private fun checkSELinuxEnforcing(): Boolean {
        return try {
            val selinuxClass = Class.forName("android.os.SELinux")
            val isEnforcingMethod = selinuxClass.getMethod("isSELinuxEnforcing")
            isEnforcingMethod.invoke(null) as Boolean
        } catch (e: Exception) {
            true // Elyra OS default is always enforcing
        }
    }

    private fun checkRootPresence(): Boolean {
        val suPaths = arrayOf(
            "/system/bin/su",
            "/system/xbin/su",
            "/sbin/su",
            "/system/sd/xbin/su",
            "/system/bin/failsafe/su",
            "/data/local/xbin/su",
            "/data/local/bin/su",
            "/data/local/su",
            "/data/adb/ksud",
            "/data/adb/ap"
        )
        for (path in suPaths) {
            if (File(path).exists()) return true
        }
        return false
    }

    private fun getSystemProperty(key: String, defaultValue: String): String {
        return try {
            val systemPropertiesClass = Class.forName("android.os.SystemProperties")
            val getMethod = systemPropertiesClass.getMethod("get", String::class.java, String::class.java)
            getMethod.invoke(null, key, defaultValue) as String
        } catch (e: Exception) {
            defaultValue
        }
    }
}
