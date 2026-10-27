---
name: performance-profiling
description: Measure and improve boot time, RAM, app launch, jank, battery and thermals with Perfetto/simpleperf/dumpsys; Game Mode; performance profiles. Use for any optimization task.
---
# Performance method
Baseline -> change ONE thing -> re-measure (N>=10 runs, same conditions) -> keep only if better -> write `docs/benchmarks/`.
## Tools
- Boot: `adb shell bootchart`/Perfetto boot trace, `logcat -b events | grep boot_progress`, `systemd-analyze`-style via `dmesg` timestamps.
- Launch: `adb shell am start -W -n <pkg>/<act>` (cold = after `am force-stop`; warm; hot). Macrobenchmark + baseline profiles for Elyra apps.
- RAM: `dumpsys meminfo`, `smaps`, `procrank`-style via `showmap`, PSS/RSS per process, `lmkd`/PSI (`/proc/pressure/memory`).
- CPU: `simpleperf`, Perfetto sched + freq traces. GPU/frames: `dumpsys gfxinfo`, FrameTimeline, Perfetto.
- Battery: `dumpsys batterystats`, Battery Historian, charge counter deltas over fixed scripted workload.
- Thermal: Thermal HAL, `dumpsys thermalservice`, log skin temp during stress.
## RAM strategy (no mass killing)
Remove unused system apps/services, avoid duplicated processes, trim SystemUI/Launcher allocations, bitmap caches sized to memory class, lazy-init, fix leaks (LeakCanary in debug builds), tune zRAM/lmkd only with measured evidence.
## Profiles
Battery Saver / Balanced / Performance / Gaming / Custom implemented with official APIs: `PowerManager` thermal APIs, ADPF `PerformanceHintManager`, `GameManager`/Game Mode interventions, cpuset/uclamp via vendor-supported power HAL hints only. Hard thermal ceiling is never overridden.
