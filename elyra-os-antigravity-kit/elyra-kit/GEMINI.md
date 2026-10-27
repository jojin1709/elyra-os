# ELYRA OS — Project Identity
Elyra OS = freedom-first, privacy-focused, fast Android OS built from AOSP + Linux kernel. Tagline: "Your device. Your rules."
It is a REAL bootable OS, never a mockup, launcher skin or emulator-only demo.

## Always-on rules
1. Read `docs/STATE.md` first every session. Update it before you stop (done / in-progress / blocked / next).
2. Work ONE phase at a time (see `docs/ROADMAP.md`). Never start phase N+1 until phase N exit criteria pass.
3. Prefer upstream AOSP. Order of preference: product config > RRO overlay > new Elyra module > patch to AOSP (last resort, keep in `patches/` with a reason).
4. Never remove SELinux, AVB, encryption or permissions to "make it work". Freedom != disabling security. Show the user the consequences instead.
5. Never claim hardware works without running the test on the real device. Mark status as untested/tested in `docs/HARDWARE_STATUS.md`.
6. Never kill apps to "optimize RAM". Measure before and after, save numbers in `docs/benchmarks/`.
7. Never trade thermal/battery safety for benchmark scores.
8. Every big decision gets an ADR in `docs/adr/NNNN-title.md`.
9. Every feature ships with a test. Compiling is not done.
10. Don't invent your own root mechanism. Use Magisk/KernelSU/APatch ecosystem + stock unlock flow.

## Languages
Kotlin (SystemUI/Launcher/Settings/apps), Java (framework where needed), C++ (native/HAL), C (kernel), Rust (security-sensitive native), AIDL (IPC/HAL), Bash/Python (tooling).

## Routing (load the agent file + its skills BEFORE working)
- Architecture/plan/ADR -> `.agent/agents/aosp-architect.md`
- Kernel/device tree/HAL/bring-up -> `kernel-bringup-engineer.md`
- SystemUI/Launcher/Settings/apps -> `android-ui-engineer.md`
- Look & feel, UX flows, motion -> `ui-ux-designer.md`
- SELinux/AVB/privacy/security center -> `security-engineer.md`
- Perf/battery/thermal/gaming -> `performance-engineer.md`
- Tests/CI/OTA/release -> `qa-release-engineer.md`
Priority: GEMINI.md > agent file > SKILL.md.

## Environment notes
- AOSP source lives OUTSIDE this repo at `$ELYRA_AOSP` (default `~/elyra-aosp`). Use terminal `rg`/`grep`, never open the whole tree in the editor.
- This repo holds: manifest, `vendor/elyra`, patches, docs, scripts, tests.
- Long builds: run in background, log to `logs/`, check tail. Never block on a full build without a plan.
- If unsure about a current AOSP detail (branch, lunch target, device support), search the web / source.android.com first. Don't guess.
