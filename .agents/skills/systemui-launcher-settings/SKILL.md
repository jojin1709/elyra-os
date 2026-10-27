---
name: systemui-launcher-settings
description: Building Elyra SystemUI, Launcher and Settings on top of AOSP (RRO, SystemUI plugins, Launcher3/Quickstep, SettingsLib, Compose). Use for any Phase 3 UI engineering.
---
# SystemUI / Launcher / Settings
## SystemUI (status bar, QS, notifications, lock screen, volume, power menu)
- Start with RRO + `config.xml` + dimens/colors/styles. Then SystemUI plugin points. Fork a component only with an ADR.
- AOSP SystemUI is Kotlin + Dagger + increasingly Jetpack Compose. Match its patterns (Interactor/ViewModel/Repository), don't invent a parallel architecture.
- Quick Settings tiles: add via `QSTileImpl` / tile services, not hacks.
## Launcher
- Base on AOSP Launcher3 + Quickstep (gestures, recents). Do NOT write from scratch. Elyra layer adds: grid/icon options, folders, search, icon packs, gesture config.
- Budget: cold start < 400ms on reference device, idle RSS tracked in benchmarks.
## Settings
- Extend AOSP Settings + `SettingsLib` with new preference screens (Privacy Center, Security Center, Performance, Elyra Features). Keep the section order from the project spec.
## Compose rules
- State hoisting, `remember` only where needed, stable types, no work in composition, `Modifier.graphicsLayer` for animations, baseline profiles for startup.
## Output per screen
Kotlin code + token usage + screenshot tests (light/dark/AMOLED, 1.0x and 2.0x font) + TalkBack labels.
