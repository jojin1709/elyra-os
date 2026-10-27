# Elyra OS — Freedom Layer: Open-Source Root Integration Guide

## 1. Principles & Architectural Stance
Elyra OS is designed to be **100% root-friendly**. We do not include proprietary root-hiding or integrity-spoofing hacks. Instead:
1. We preserve standard Android Treble and GKI partition layouts.
2. Root presence is detected and honestly displayed in **Elyra Security Center** so the user always knows their exact device security boundary.
3. We do **not** ship a pre-rooted system build by default, ensuring security-sensitive users retain an untouched platform while granting power users full access to root.

---

## 2. Recommended Root Solutions

| Solution | Mechanism | Integration Point | Recommended Use |
| :--- | :--- | :--- | :--- |
| **KernelSU** | Kernel driver hooks | Kernel Image (`boot.img` or GKI module) | **Best for stealth & minimal userspace overhead** |
| **APatch** | KernelPatch via eBPF/Kallsyms | `boot.img` / `init_boot.img` | **High performance & modern module manager** |
| **Magisk** | Userspace ramdisk overlay | `init_boot.img` | **Mature module ecosystem and Zygisk support** |

---

## 3. Patching Flow for Android 15 & Pixel 7 (init_boot)

> [!IMPORTANT]
> **Modern Android Partition Scheme:**
> On devices launching with Android 13+ (including Pixel 7 `panther`), the first-stage ramdisk resides in **`init_boot.img`**, while **`boot.img`** contains only the Generic Kernel Image (GKI). Patch **`init_boot.img`**, NOT `boot.img`.

### Step-by-Step Instructions

#### Step 1: Extract `init_boot.img`
Obtain the `init_boot.img` matching your current Elyra OS build from the release zip:
```bash
unzip elyra-panther-img-*.zip init_boot.img
```

#### Step 2: Push to Device & Patch
1. Push `init_boot.img` to your device storage:
   ```bash
   adb push init_boot.img /sdcard/Download/
   ```
2. Open the **Magisk** or **APatch** application on your device.
3. Select **Install > Select and Patch a File**.
4. Choose the `init_boot.img` from your Downloads folder.
5. Pull the patched output image back to your computer:
   ```bash
   adb pull /sdcard/Download/magisk_patched_*.img patched_init_boot.img
   ```

#### Step 3: Safe Temporary Boot Test
Never flash directly without testing! Test boot the patched image in fastboot mode:
```bash
adb reboot bootloader
fastboot boot patched_init_boot.img
```
*If the device boots successfully to the home screen and root is confirmed in the app, proceed to permanently flash.*

#### Step 4: Permanent Installation
Flash to the active `init_boot` partition:
```bash
fastboot flash init_boot patched_init_boot.img
fastboot reboot
```

---

## 4. Security Center Status Representation
Once rooted, **Elyra Security Center** will update:
- **Root Status Badge**: `ROOT ACTIVE` (Superuser binaries accessible).
- **Security Guidance**: A calm informational note explains that banking or enterprise applications utilizing Play Integrity may require user configuration.
- **SELinux Status**: Remains strictly **Enforcing** (Magisk and KernelSU run policies within isolated root domains without degrading core SELinux protections).
