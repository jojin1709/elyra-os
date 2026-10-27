---
description: Phase 0 - produce the Elyra OS Master Architecture & Development Plan
---
1. Read GEMINI.md, docs/ELYRA_SPEC.md (if present), docs/STATE.md. Load agent `aosp-architect` + skills.
2. Web-search CURRENT facts: latest stable AOSP release branch, AOSP device support list, bootloader-unlock/custom-AVB-key support for candidate devices, kernel source availability.
3. Shortlist 3 reference devices. Score each (unlock, kernel src, AOSP support, relock w/ custom key, recovery, community). Write ADR 0001 (device) and ADR 0002 (Android version). STOP and ask the user to confirm both choices.
4. Write `docs/ARCHITECTURE.md` with the 15 layers and 6 tables: AOSP reuse / AOSP modify / Elyra create / device-vendor / optional / experimental.
5. Write: repo layout (apply `elyra-repo-layout`), coding standards, test strategy, risk register (top 15 risks + mitigation), dependency list, first 30 tasks ordered.
6. Write `docs/design/00-design-language.md` from skill `elyra-ui-ux-design-system`, plus 12 screen specs list for P3.
7. Update docs/STATE.md. Do NOT write platform code in this workflow.
