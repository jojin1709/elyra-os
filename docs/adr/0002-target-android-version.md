# ADR 0002: Target Android Platform Version and Branch Selection

## Status
PROPOSED (Awaiting User Confirmation)

## Context
Elyra OS requires a baseline upstream AOSP version and specific release branch. The platform version dictates:
1. Long-term security patch lifecycle (ASB).
2. Hardware compatibility, GKI (Generic Kernel Image) version, and vendor HAL stability.
3. Modern system frameworks: Private Space, credential management, notification routing, Jetpack Compose in SystemUI, and AppOps granularity.
4. Toolchain requirements (Clang 18+, Rust 1.77+, OpenJDK 21/17).

## Comparison: Android 14 vs. Android 15

| Dimension | Android 14 (API 34 - Upside Down Cake) | Android 15 (API 35 - Vanilla Ice Cream) |
| :--- | :--- | :--- |
| **AOSP Tag / Branch** | `android-14.0.0_r50` (or `android-14.0.0_r68`) | `android-15.0.0_r17` (or current stable maintenance tag) |
| **Linux Kernel Base** | GKI Linux 5.15 / 6.1 | GKI Linux 6.1 / 6.6 |
| **Pixel Driver Binaries** | Mature, highly stable | Officially released for Pixel 6 through 9 |
| **Modern Privacy Features** | Basic Privacy Dashboard, AppOps | Native Private Space, granular screen sharing, AppOps 2.0 |
| **SystemUI Stack** | Kotlin + Dagger + early Compose | Mature Compose integration, edge-to-edge standard |
| **Longevity & Support** | Approaching maintenance-only lifecycle | Active primary lifecycle (3+ years forward runway) |
| **Build Stability** | 100% predictable | High stability on Pixel 7/8 targets |

## Decision
**Primary Target**: **Android 15 (API Level 35)**
- **Baseline Manifest Branch/Tag**: `android-15.0.0_r17` (Vanilla Ice Cream)
- **Conservative Fallback**: `android-14.0.0_r50` (if unresolvable vendor blob or toolchain friction occurs on host)
- **Lunch Targets**:
  - Reference Physical Device (Pixel 7): `aosp_panther-userdebug` (or `panther-trunk_staging-userdebug`)
  - Virtual Development Target (Cuttlefish): `aosp_cf_x86_64_phone-userdebug`

## Consequences
- Targeting Android 15 gives Elyra OS modern architecture (Private Space, edge-to-edge UI paradigms, enhanced SELinux bounds, GKI 6.1).
- Host build environment requires Ubuntu 22.04/24.04 LTS or WSL2 with Python 3.10+, Git 2.34+, and sufficient storage/memory.
- Any UI components developed in Phase 3 will follow Android 15's window insets and edge-to-edge architecture from day one.
