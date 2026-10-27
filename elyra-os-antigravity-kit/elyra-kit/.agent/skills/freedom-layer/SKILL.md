---
name: freedom-layer
description: Bootloader unlock, root-friendly, custom kernel/recovery, adb/fastboot developer experience and honest security state for Elyra. Use for Phase 5.
---
# Freedom layer ("Your device. Your rules.")
- Ship: OEM-unlock path docs, `fastboot` flashing guide, recovery + sideload docs, kernel build/flash guide, ADB/dev options pre-organized in Settings > Developer Options.
- Root: stay compatible with Magisk / KernelSU / APatch. Document patching boot/init_boot image. Do NOT ship a custom root daemon or bundle a root app by default.
- Two modes, always visible:
  - Normal: locked bootloader, verified boot green, SELinux enforcing.
  - Advanced/Modified: unlocked and/or rooted/custom kernel. Security Center shows an amber banner + exact consequences (e.g. banking/Play Integrity apps may fail, verified boot can't protect against tampering, data is exposed if device is lost).
- First-boot on an unlocked device: show one calm explanation screen, no scare dialogs, no nag loops.
- Never silently disable a security feature. Any toggle that lowers security needs a confirm sheet with consequences and a one-tap way back.
- Dev tools: Terminal app (Termux-like, sandboxed), wireless debugging, bug report shortcut, logcat viewer.
