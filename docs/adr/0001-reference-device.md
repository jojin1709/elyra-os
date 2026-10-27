# ADR 0001: Reference Device Selection for Elyra OS

## Status
PROPOSED (Awaiting User Confirmation)

## Context
Elyra OS is an independent, real bootable operating system built from AOSP and the Linux kernel. To develop, test, and validate Elyra OS across Phases 1 through 10, a primary physical reference device is required alongside virtualized emulator targets (Cuttlefish).

The reference device must satisfy strict criteria:
1. **Bootloader Unlocking**: Zero-friction OEM unlock via standard `fastboot flashing unlock` without proprietary unlock codes or tokens.
2. **Custom AVB Root of Trust**: Hardware support for flashing a custom AVB 2.0 public key (`fastboot flash avb_custom_key pkmd.bin`) and re-locking the bootloader (`fastboot flashing lock`) to achieve full verified boot on custom builds.
3. **Official AOSP Driver Binaries**: Vendor proprietary image packages provided directly by Google on `developers.google.com/android/drivers`.
4. **Kernel & Device Tree Availability**: Full open-source kernel repository (GKI-compliant) and upstream AOSP device trees (`device/google/*`).
5. **Recovery & Anti-Brick Protection**: Low-level recovery via Fastboot, Factory Images, and USB recovery tools.
6. **Community & Economic Accessibility**: Broad developer adoption, active aftermarket availability, and mature tooling.

## Candidate Evaluation

| Criteria (Weight) | Candidate 1: Google Pixel 7 ("panther") | Candidate 2: Google Pixel 7a ("lynx") | Candidate 3: Google Pixel 8 ("shiba") |
| :--- | :--- | :--- | :--- |
| **SoC / Arch** | Google Tensor G2 (4nm / ARMv8.2) | Google Tensor G2 (4nm / ARMv8.2) | Google Tensor G3 (4nm / ARMv9) |
| **Bootloader Unlock** | 10/10 (Standard Fastboot) | 10/10 (Standard Fastboot) | 10/10 (Standard Fastboot) |
| **Custom AVB Key Re-lock** | 10/10 (Titan M2 `avb_custom_key`) | 10/10 (Titan M2 `avb_custom_key`) | 10/10 (Titan M2 `avb_custom_key`) |
| **Official AOSP Drivers** | 10/10 (Android 14 & 15 Binaries) | 10/10 (Android 14 & 15 Binaries) | 10/10 (Android 14 & 15 Binaries) |
| **Upstream AOSP Trees** | 10/10 (`device/google/pantah`) | 10/10 (`device/google/pantah`) | 9/10 (`device/google/shiba`) |
| **Kernel Source (GKI)** | 10/10 (`android14-5.15` / `android15-6.1`) | 10/10 (`android14-5.15` / `android15-6.1`) | 9/10 (`android14-6.1` / `android15-6.6`) |
| **Secondary Market Price** | ~\$180–\$220 USD | ~\$190–\$240 USD | ~\$320–\$380 USD |
| **Recovery / Unbrick** | 10/10 (WebUSB / Flash Tool / Factory) | 10/10 (WebUSB / Flash Tool / Factory) | 10/10 (WebUSB / Flash Tool / Factory) |
| **Thermal & Display (90Hz/120Hz)** | 9/10 (90Hz OLED, stable thermals) | 8/10 (90Hz OLED, higher thermals) | 10/10 (120Hz Actua OLED) |
| **TOTAL SCORE (out of 100)** | **97/100 (Recommended)** | **94/100** | **91/100** |

### Emulator Companion Target: Google Cuttlefish (`aosp_cf_x86_64_phone`)
In addition to the physical reference device, Google Cuttlefish is designated as the primary virtual integration target. This enables compiling and testing SystemUI, Launcher, and Settings on x86_64 without requiring a physical device attached, matching the host development workstation.

## Decision
**Primary Recommended Reference Device**: **Google Pixel 7 (Codename: `panther`)**
- **Fallback / Secondary Physical Device**: Google Pixel 7a (`lynx`) or Pixel 8 (`shiba`).
- **Virtual Development Target**: Google Cuttlefish (`cf_x86_64_phone-trunk_staging-userdebug` / `aosp_cf_x86_64_phone-userdebug`).

## Consequences
- Pixel 7 provides the optimal balance of modern hardware (Tensor G2, Titan M2 secure element, hardware biometric integration, GKI kernel), complete AOSP upstream support for both Android 14 and Android 15, and the lowest cost barrier for test hardware.
- Both Pixel 7 and 7a share the common `pantah` hardware repository base in AOSP, making dual-device support straightforward.
