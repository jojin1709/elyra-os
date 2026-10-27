---
name: elyra-repo-layout
description: Defines where Elyra code lives (vendor/elyra, device trees, patches, manifest) and the order of customization (config > RRO > module > patch). Use when adding any Elyra component.
---
# Elyra repository layout
```
elyra-workspace/            (this repo)
  manifest/                 local_manifests + pinned manifests
  vendor/elyra/             -> synced into $ELYRA_AOSP/vendor/elyra
    elyra_common.mk         product inherit, packages, properties
    overlays/               RRO: framework-res, SystemUI, Settings, Launcher
    sepolicy/               Elyra SELinux policy
    apps/                   ElyraLauncher, ElyraSettings*, ElyraFiles, ElyraUpdater, ...
    services/               system services (AIDL), Rust/C++ daemons
    prebuilts/ fonts/ wallpapers/ sounds/ icons/
  device/<oem>/<codename>/  device tree (own repo)
  kernel/<oem>/<codename>/  kernel (own repo)
  patches/                  last-resort AOSP patches, each with docs/adr link
  scripts/  tests/  docs/   adr/ benchmarks/ design/ plans/
```
## Customization order (stop at the first that works)
1. Product config / system properties / `config.xml` values
2. RRO overlay
3. New Elyra module (app, service, plugin) in vendor/elyra
4. Small, rebased patch to AOSP in `patches/` with ADR and upstream-risk note
## Modularity
Core (always): Android system, Elyra SystemUI, Settings, core security. Optional packages behind `PRODUCT_PACKAGES` flags in `elyra_common.mk` / `elyra_optional.mk`: Google apps (user-installed package, not bundled), AI, gaming tools, extras, dev tools.
