---
name: elyra-framework-services
description: How to add Elyra system services, AIDL interfaces and privileged system apps safely (Privacy Center data, Performance Engine, Updater). Use when creating framework-level or native services.
---
# Elyra framework services
- Prefer a privileged system APP with `android:sharedUserId`-free design + signature|privileged permissions over modifying `frameworks/base`.
- Needs a real system service? Put it in `vendor/elyra/services/<name>`, expose a stable AIDL interface (`@VintfStability` only if crossing the vendor boundary), version it (`aidl_interface` with `versions_with_info`).
- Native daemons: C++ or Rust (`rust_binary`), own `.rc` file, own SELinux domain (`init_daemon_domain`), minimal capabilities, no root unless justified.
- Privacy Center data comes from existing sources first: AppOps (`AppOpsManager`), Permission Hub / `PermissionController`, `dumpsys`-free APIs, Privacy Indicators. Add new hooks only if no public/system API exists (ADR).
- Permissions: define `signature|privileged` custom permissions; add privapp-permissions XML allowlist; sign with platform key via `certificate: "platform"` only when required.
- Every service ships: Android.bp, sepolicy, init rc, unit test (`atest`), and a section in docs/architecture.
