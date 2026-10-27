package org.elyra.securitycenter.model

enum class BootStateGrade {
    SECURE_GREEN,     // Locked official
    CUSTOM_YELLOW,    // Locked custom AVB key
    UNLOCKED_ORANGE,  // Unlocked bootloader
    TAMPERED_RED      // Integrity failure
}

data class SecurityState(
    val bootStateGrade: BootStateGrade = BootStateGrade.SECURE_GREEN,
    val isBootloaderLocked: Boolean = true,
    val verifiedBootStateString: String = "green",
    val isSELinuxEnforcing: Boolean = true,
    val isStorageEncrypted: Boolean = true,
    val securityPatchLevel: String = "2026-10-01",
    val isAdbEnabled: Boolean = false,
    val isRootDetected: Boolean = false,
    val buildId: String = "ELYRA-15.0-RELEASE"
)
