---
trigger: always_on
---
# Engineering rules
- Plan first for anything touching more than 3 files: write a short plan in `docs/plans/<slug>.md`, then implement.
- Keep Elyra code modular under `vendor/elyra/` (packages, overlays, sepolicy, product makefiles, prebuilts).
- Conventional commits (`feat:`, `fix:`, `perf:`, `docs:`, `test:`). One logical change per commit.
- Kotlin: ktlint/detekt clean. C++: clang-format (AOSP style). Rust: rustfmt + clippy clean. Shell: shellcheck clean.
- No dependency without a stated technical reason in the ADR.
- If a build or test fails, read the real log, find the root cause. Don't retry blindly and don't comment out the failing test.
- Never fake results. If something is untested, say untested.
