---
name: ota-ab-updates
description: Signed A/B (virtual A/B) OTA generation, update_engine client app, rollback protection and failure recovery for Elyra.
---
# OTA
- Use stock `update_engine` (A/B or Virtual A/B). Elyra Updater is a privileged app using `UpdateEngine` API; it does NOT reimplement installation.
- Build: `m otapackage`; full OTA via `ota_from_target_files`; incremental with `-i <old_target_files.zip>`; payload properties for streaming updates (`brillo_update_payload`).
- Signing: release keys from `$ELYRA_KEYS`; `sign_target_files_apks` + `ota_from_target_files`. Verify signature on-device before apply (update_engine does; test with a tampered payload).
- Rollback protection: AVB rollback index increments with each security patch; refuse downgrade unless user-initiated wipe flow.
- Failure safety: update writes inactive slot; boot marks slot successful only after health check (`markBootSuccessful` after first stable boot/boot_control HAL); failed boot auto-falls back to old slot.
- Update server: static signed files + JSON manifest (version, patch level, changelog, hashes, size). Host on cheap static hosting; HTTPS only; pin the manifest signing key.
- UX: show version, patch level, changelog, size, battery/storage preconditions, history log, "installing in background" state, clear failure reason.
- Required tests: normal update, interrupted power, corrupted payload, downgrade attempt, low storage, no network resume.
