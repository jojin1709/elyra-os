---
description: Review a screen or flow against the Elyra design system and accessibility rules
---
1. Load `ui-ux-designer` + design system skill.
2. Get screenshots (Cuttlefish `adb exec-out screencap -p`) for light/dark/AMOLED and 2.0x font.
3. Check: tokens only, hierarchy, spacing, contrast, touch targets, copy tone, empty/loading/error states, motion, TalkBack order.
4. Output a table: issue | severity | fix. Apply fixes for P0/P1 issues, re-shoot, confirm.
