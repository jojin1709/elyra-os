---
description: Build Elyra (Cuttlefish first, then reference device) and report results
---
1. Load `aosp-architect` + skill `aosp-build-env`.
2. Apply vendor/elyra into $ELYRA_AOSP (`scripts/sync-vendor.sh`).
3. lunch the target from docs/STATE.md, run the build in background with logging.
4. On failure: show the first real error, fix root cause, rebuild incrementally. Max 3 attempts, then summarize and ask.
5. On success: boot on Cuttlefish (`scripts/run-cuttlefish.sh`), capture boot time + logcat errors, update STATE.md.
