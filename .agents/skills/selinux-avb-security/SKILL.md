---
name: selinux-avb-security
description: SELinux policy, Android Verified Boot, encryption, privacy/security dashboards for Elyra. Use for sepolicy denials, AVB signing, Security Center/Privacy Center work.
---
# SELinux, AVB, encryption
## SELinux
- Enforcing always. Triage denials: `adb logcat -b events | grep avc` / `dmesg | grep avc`; use `audit2allow` only as a HINT, then write minimal rules by hand.
- New domain per daemon/app type; labels in `file_contexts`, `service_contexts`, `property_contexts`; policy under `vendor/elyra/sepolicy/{public,private,vendor}`.
- Never weaken `neverallow`. Run `sepolicy` build checks + CTS SELinux tests.
## AVB / Verified Boot
- Elyra release keys: generated offline, stored outside repo (`$ELYRA_KEYS`), backed up. Build signs vbmeta + images with them (`BOARD_AVB_*` vars).
- Rollback index bumped every security release.
- Document relock-with-custom-key per device (only where the bootloader supports it).
## Encryption
FBE (+ metadata encryption where device supports). Never ship builds that disable it.
## Security Center data sources
- `ro.boot.verifiedbootstate` (green/yellow/orange/red), `ro.boot.flash.locked`, `ro.boot.vbmeta.device_state`, `getenforce`/SELinux API, `ro.build.version.security_patch`, storage encryption state (`StorageManager`), `Settings.Global.ADB_ENABLED`, `DEVELOPMENT_SETTINGS_ENABLED`, unknown-source install permissions, Play Integrity/Key attestation status where available, root presence via mount/su-binary heuristics (report, don't hide).
- Each item: state, plain-language meaning, what changes when it's not the default.
## Privacy Center data sources
AppOps/permission usage history, privacy indicators (camera/mic/location), `ClipboardManager` access events, nearby devices permission, sensor access, background activity from usage stats. Show timeline per app + one-tap revoke.
