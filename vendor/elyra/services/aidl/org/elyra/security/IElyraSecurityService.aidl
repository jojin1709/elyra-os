package org.elyra.security;

/**
 * Elyra Security Engine AIDL Interface
 * Central provider for device integrity, AVB 2.0 status, root telemetry, and encryption verification.
 */
interface IElyraSecurityService {
    /**
     * Verified Boot State:
     * 0 = GREEN (Stock locked)
     * 1 = YELLOW (Custom AVB root-of-trust key, locked)
     * 2 = ORANGE (Unlocked bootloader)
     * 3 = RED (Tampered / verification failure)
     */
    int getVerifiedBootState();

    /** Returns true if the device bootloader is physically locked. */
    boolean isBootloaderLocked();

    /** Returns true if SELinux is actively in Enforcing mode. */
    boolean isSELinuxEnforcing();

    /** Returns true if File-Based Encryption (FBE) is active on user storage. */
    boolean isStorageEncrypted();

    /** Returns the current Android security patch level string (YYYY-MM-DD). */
    String getSecurityPatchLevel();

    /** Returns true if ADB or wireless debugging is enabled. */
    boolean isAdbEnabled();

    /** Returns true if known root binaries (su/KernelSU/APatch) are present. */
    boolean isRootDetected();
}
