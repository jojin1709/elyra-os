---
description: Prepare this Linux machine for AOSP builds and sync the source
---
1. Run `scripts/check-host.sh` and report RAM/disk/CPU. Stop if disk < 400GB free; tell the user.
2. Run `scripts/setup-host.sh` (asks for sudo itself).
3. Read ADR 0002 for the branch. `repo init` + `repo sync` into $ELYRA_AOSP in the background, log to logs/sync.log.
4. Verify with `source build/envsetup.sh && lunch` (list targets). Record the chosen target in docs/STATE.md.
