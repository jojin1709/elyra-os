---
description: Generate a signed OTA and run the failure-injection test matrix
---
1. Load `qa-release-engineer` + `ota-ab-updates`.
2. Build target-files, create full + incremental OTA with release keys (test keys for dev builds, clearly labeled).
3. Run on device: normal update, power cut mid-update, corrupted payload, downgrade attempt, low storage, network drop/resume.
4. Write `docs/test-reports/ota-<date>.md` with pass/fail per case and slot/boot logs.
