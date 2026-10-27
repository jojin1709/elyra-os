---
trigger: always_on
---
# Testing rules
- A feature is done only with: unit/instrumentation test + manual device check noted in `docs/HARDWARE_STATUS.md` (if hardware related).
- Use `atest` for host/device tests, Cuttlefish for boot/system tests, CTS/VTS subsets before releases.
- Perf work needs a baseline and a result in `docs/benchmarks/<date>-<topic>.md` (device, build id, method, numbers, N runs).
- Reliability suite before any release candidate: reboot loop (50x), OTA failure injection, low storage, low memory, network loss, low battery.
