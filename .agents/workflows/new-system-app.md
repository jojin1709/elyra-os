---
description: Create a new Elyra first-party system app (Files, Gallery, Updater, Terminal, ...)
---
1. Check docs/ARCHITECTURE.md: does an AOSP app already cover this? If yes, theme/extend it instead of duplicating.
2. Write `docs/design/<app>.md` (purpose, screens, states, tokens) using the design system.
3. Scaffold under vendor/elyra/apps/<App>: Android.bp, manifest, privapp-permissions, sepolicy if needed.
4. Implement in Kotlin + Compose. Add unit tests + screenshot tests (light/dark/AMOLED, 2.0x font).
5. Add to `elyra_common.mk` (core) or `elyra_optional.mk`. Build the module with `m <App>` and run `atest`.
6. Perf check: cold start time + idle RAM, log in docs/benchmarks/.
