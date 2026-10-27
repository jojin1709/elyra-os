# Elyra OS — Risk Register & First 30 Tasks

## 1. Top 15 Technical Risk Register & Mitigations

| # | Risk Description | Severity | Probability | Impact | Architectural Mitigation |
| :- | :--- | :---: | :---: | :--- | :--- |
| **R01** | Host disk exhaustion during full AOSP build (requires 400GB+ SSD). | Critical | High | Build abortion, corrupted ccache. | Segregate builds to dedicated high-capacity storage; use Cuttlefish for modular builds; maintain `--depth=1` shallow checkouts. |
| **R02** | Device bricking during early bootloader/kernel flashing. | High | Low | Hardware failure, loss of reference test device. | Never flash without validating active slot; always test via `fastboot boot boot.img` prior to permanent flashing; maintain stock unbrick factory images. |
| **R03** | SELinux `neverallow` assertion failure blocking platform build. | High | Medium | Build failure; temptation to weaken security. | Adhere to zero-neverallow modification policy; isolate custom daemons into dedicated domains with minimal permissions. |
| **R04** | Vendor proprietary blob incompatibility on Android 15. | High | Medium | Broken camera, audio, or cellular modem. | Use exact official driver packages matched to AOSP build ID from `developers.google.com/android/drivers`; maintain Android 14 fallback. |
| **R05** | Bootloader re-locking failure with custom AVB key (`avb_custom_key`). | High | Medium | Unbootable device with locked bootloader. | Test custom key verification on unlocked bootloader first; confirm yellow-boot state before executing `fastboot flashing lock`. |
| **R06** | SystemUI crash loop on boot (Keyguard / Statusbar null pointer). | Medium | Medium | Inability to reach launcher, boot hang. | Use isolated ViewModels with fallback states; write screenshot and Robolectric tests for all UI views before integrating into image. |
| **R07** | Memory pressure / Out-of-Memory kills on 8GB reference device. | Medium | Low | Background apps terminated, poor multitasking. | Optimize memory cgroups; avoid heavy custom daemons; enforce strict cold-start memory budgets on ElyraLauncher and SystemUI. |
| **R08** | Incomplete hardware sensor calibration (gyroscope, ALS, proximity). | Medium | Low | Auto-brightness failure, screen rotation glitch. | Direct reuse of vendor sensor HAL; run VTS sensor verification tests early in Phase 2. |
| **R09** | OTA payload signature mismatch or rollback loop. | Critical | Low | Device fails update, recovery rollback loop. | Automated dual-slot test in Phase 7; verify payload hashes and rollback index bumping before distributing OTA packages. |
| **R10** | Thermal throttling during extended compilation or gaming workloads. | Medium | Medium | Reduced clock frequencies, battery drain. | Honor OEM thermal throttling policies; never overwrite hardware thermal engine tables in `/vendor/etc/thermal*`. |
| **R11** | App compatibility issues due to Play Integrity API failure on unlocked devices. | Medium | High | Banking / enterprise apps refusal. | Transparently warn user in Security Center; provide sandboxed MicroG compatibility layer; maintain honest security status without fake spoofing. |
| **R12** | Display composition jitter or dropped frames in gesture navigation. | Medium | Low | Janky user experience. | Maintain upstream SurfaceFlinger pipeline; utilize hardware composer (HWC); test 90Hz/120Hz refresh rates with Systrace / Perfetto. |
| **R13** | Storage encryption key loss during dirty flashes. | Critical | Low | Total data loss on `/data`. | Enforce standard Android FBE; prompt user for backup before platform version upgrades; maintain clean partition boundaries. |
| **R14** | Bluetooth audio / codec (LDAC/AptX) negotiation failure. | Low | Medium | Distorted sound, dropped connection. | Verify A2DP HAL endpoints against Bluetooth CTS suite; retain stock Bluetooth stack (`com.android.btservices`). |
| **R15** | Battery drain caused by rogue background wake locks in system services. | High | Medium | Reduced battery life. | Profile background threads with `dumpsys batterystats` and Batterystats historian; avoid persistent polling loops; use JobScheduler/WorkManager. |

---

## 2. Upstream & Toolchain Dependencies
1. **Upstream AOSP Manifest**: `https://android.googlesource.com/platform/manifest` (Branch: `android-15.0.0_r17` / `android-14.0.0_r50`).
2. **Device Hardware Trees**: `device/google/pantah`, `device/google/pantah-kernels`, `device/google/shiba`.
3. **Proprietary Vendor Binaries**: Google Tensor G2 / Titan M2 driver packages (Qcom/Broadcom/Google blobs).
4. **Linux Kernel GKI**: Android Common Kernel `common-android15-6.1` / `common-android14-5.15`.
5. **Cryptographic Tooling**: OpenSSL, `avbtool`, `sign_target_files_apks`, `otatools`.

---

## 3. Ranked First 30 Development Tasks

### Phase 0: Architecture & Foundation (Tasks 1–6)
- [x] **Task 01**: Establish project identity, guidelines, and load agent skills into `.agents`.
- [x] **Task 02**: Draft Product Specification (`docs/ELYRA_SPEC.md`).
- [x] **Task 03**: Author ADR 0001 (Reference Device Selection) and ADR 0002 (Target Android Version).
- [x] **Task 04**: Produce Master Architecture specification (`docs/ARCHITECTURE.md`) with 15 layers and 6 matrices.
- [x] **Task 05**: Define Repo Layout, Standards, Testing Strategy, and Risk Register (`docs/plans/`).
- [x] **Task 06**: Author Elyra Design Language Specification (`docs/design/00-design-language.md`) and 12-screen UI catalog.

### Phase 1: Environment & Initial Boot (Tasks 7–13)
- [ ] **Task 07**: Configure host build environment and execute `scripts/setup-host.sh`.
- [ ] **Task 08**: Initialize external AOSP repo checkout (`repo init` + `repo sync`) with shallow clone.
- [ ] **Task 09**: Configure local manifest linking `vendor/elyra` into `$ELYRA_AOSP`.
- [ ] **Task 10**: Extract official driver binaries for reference device into `$ELYRA_AOSP/vendor/google_devices/`.
- [ ] **Task 11**: Build baseline Cuttlefish emulator image (`aosp_cf_x86_64_phone-userdebug`) and verify boot.
- [ ] **Task 12**: Build baseline reference physical device image (`aosp_panther-userdebug`).
- [ ] **Task 13**: Execute first boot validation on physical hardware via `fastboot boot`; confirm 20 clean reboots.

### Phase 2: Hardware Bring-Up & Validation (Tasks 14–18)
- [ ] **Task 14**: Validate display resolution, touch digitization, and 90Hz refresh rate.
- [ ] **Task 15**: Validate USB-C connectivity, ADB, fastbootd, and MTP file transfer.
- [ ] **Task 16**: Validate Audio HAL (stereo speakers, earpiece, microphone, USB-C audio).
- [ ] **Task 17**: Validate Radios (Wi-Fi 6E, Bluetooth 5.2, GPS/GNSS, Cellular VoLTE).
- [ ] **Task 18**: Validate Biometrics (Titan M2 KeyMint, under-display optical fingerprint).

### Phase 3: System Experience & Core UI (Tasks 19–25)
- [ ] **Task 19**: Implement single-source design tokens in `vendor/elyra/design/tokens.json`.
- [ ] **Task 20**: Generate and verify static RRO overlays for `framework-res` and `SystemUI`.
- [ ] **Task 21**: Implement Elyra Status Bar and Quick Settings panel with custom Elyra tiles.
- [ ] **Task 22**: Implement Elyra Keyguard, Lock Screen, and Ambient Display.
- [ ] **Task 23**: Build `ElyraLauncher` with fluid gesture navigation, custom grid, and icon support.
- [ ] **Task 24**: Build `ElyraSettings` integrating core preference trees and AMOLED theme.
- [ ] **Task 25**: Run automated UI screenshot tests across Light, Dark, and AMOLED themes.

### Phase 4 & Beyond: Privacy, Security & Release (Tasks 26–30)
- [ ] **Task 26**: Implement `ElyraPrivacyCenter` and real-time AppOps monitoring daemon.
- [ ] **Task 27**: Implement `ElyraSecurityCenter` displaying AVB grade, encryption state, and patch level.
- [ ] **Task 28**: Enforce strict SELinux policy; resolve all AVC denials; verify zero neverallow breaches.
- [ ] **Task 29**: Configure A/B update engine, offline cryptographic keys, and rollback test.
- [ ] **Task 30**: Execute 2-week daily-driver stability test and compile final release notes.
