package org.elyra.settings.data

import org.elyra.settings.model.SettingsState

class SettingsRepository {

    fun getSettingsState(): SettingsState {
        val modelProp = getSystemProperty("ro.product.model", "Pixel 7")
        val elyraVerProp = getSystemProperty("ro.elyra.version", "1.0.0-alpha")
        val buildIdProp = getSystemProperty("ro.build.display.id", "ELYRA-15.0-userdebug")

        return SettingsState(
            isAmoledEnabled = true,
            deviceModel = modelProp,
            elyraVersion = elyraVerProp,
            buildNumber = buildIdProp
        )
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
