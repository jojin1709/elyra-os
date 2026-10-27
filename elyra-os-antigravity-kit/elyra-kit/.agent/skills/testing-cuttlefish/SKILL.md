---
name: testing-cuttlefish
description: Test strategy and tooling for Elyra: Cuttlefish, atest, CTS/VTS/Tradefed, SELinux checks, fuzzing, UI screenshot tests and reliability suites.
---
# Testing
## Layers
- Host/unit: `atest <module>` (Robolectric for UI logic, GoogleTest for native, `cargo test`/Soong rust tests).
- Device/instrumentation: `atest` with device or Cuttlefish.
- System boot: Cuttlefish (`launch_cvd`, `cvd` tool) boots the same build for fast iteration; real device for hardware.
- Compatibility: CTS/VTS subsets via Tradefed (`cts-tradefed run cts -m <module>`). Full CTS is a release gate, not a per-commit gate.
- Security: SELinux CTS/neverallow checks, `cc_fuzz`/`rust_fuzz` targets for parsers & IPC, static analysis (clang-tidy, clippy, Android Lint, detekt).
- UI: Compose screenshot tests (Roborazzi/Paparazzi) for every screen in light/dark/AMOLED and 2.0x font.
- Reliability: scripted reboot loop 50x, OTA failure injection, low storage (<500MB), low memory, airplane toggling, low battery.
## CI (scripts/ci/)
1. lint+unit on every commit. 2. Cuttlefish boot smoke on every merge. 3. nightly: full unit + perf smoke + reliability subset. 4. release: CTS subset + OTA + real-device hardware matrix.
## Reporting
Results to `docs/test-reports/<date>-<build>.md`: pass/fail counts, flaky list, links to logs.
