---
name: device-bringup
description: Boot chain, kernel, device tree, vendor blobs, Treble/VINTF, fastboot flashing and recovery for the reference device. Use for Phase 1-2 and any hardware issue.
---
# Device bring-up
## Choose the reference device (ADR required)
Hard requirements: unlockable bootloader, public kernel source, AOSP-compatible device tree/vendor binaries, relock with custom AVB key preferred, recoverable flashing, active community. Verify CURRENT support by searching the web, don't rely on memory. Pixel devices supported by AOSP are the lowest-risk default.
## Order of work
1. Backup stock firmware + document the stock partition layout (`fastboot getvar all`, `adb shell ls -l /dev/block/by-name`).
2. Build the device tree from the official AOSP device/vendor sources for that device; vendor blobs from the vendor's official images (extract, never rewrite).
3. Build kernel (clang/LLVM, GKI where the device uses it); keep the kernel in its own repo with defconfig fragments in-tree.
4. Boot test with `fastboot boot` BEFORE flashing anything permanent.
5. Flash to inactive slot (A/B), keep the other slot as fallback. Keep a documented unbrick path.
6. Bring up in this order: display+touch -> adb/USB -> storage/FBE -> Wi-Fi -> audio -> BT -> sensors -> camera -> cellular/GPS -> NFC/fingerprint -> charging/thermal.
## Debug toolkit
`adb logcat -b all`, `dmesg`, `adb shell getprop`, `pstore/last_kmsg`, `lshal`, `dumpsys`, `vintf` checks (`checkvintf`), `fastboot logcat` where supported.
## Rules
- Never claim a component works without the real-device test. Update HARDWARE_STATUS.md with date + build id.
- Never flash without confirming the device codename and slot first.
