---
description: Phase 1-2 - boot and bring up hardware on the reference device
---
1. Load `kernel-bringup-engineer` + skill `device-bringup`.
2. Confirm device codename (`fastboot getvar product`) and active slot before ANY flash. Show the exact commands and wait for user OK before the first flash.
3. `fastboot boot` test image first. Then flash inactive slot.
4. Work the bring-up order in the skill. After each component: run a real test, update docs/HARDWARE_STATUS.md.
5. Stop at any unrecoverable-risk step (bootloader, modem, persist, vbmeta) and ask the user.
