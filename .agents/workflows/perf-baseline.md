---
description: Capture or compare performance baseline (boot, RAM, launch, jank, battery, thermal)
---
1. Load `performance-engineer` + `performance-profiling`.
2. Use `scripts/perf/` collectors, N>=10 runs, device cool, same brightness/network.
3. Write `docs/benchmarks/<date>-<topic>.md` with method + numbers + delta vs previous.
4. Recommend only changes backed by the numbers.
