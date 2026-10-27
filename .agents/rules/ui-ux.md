---
trigger: model_decision
description: Apply when creating or changing ANY screen, layout, animation, icon or text visible to the user (SystemUI, Launcher, Settings, system apps).
---
# UI/UX rules (details in skill `elyra-ui-ux-design-system`)
- Premium + minimal + fast + functional. NOT cyberpunk, hacker-themed, neon, heavy glass blur, or cluttered dashboards.
- Use design tokens only (colors, type, spacing, radius, motion). No hard-coded hex/dp in screens.
- Every screen must have: light, dark and AMOLED variants; empty, loading and error states; 200% font scale check; TalkBack labels; 48dp touch targets.
- 60 fps minimum (target the device refresh rate). No jank on first open. Heavy work off the main thread.
- Plain language. Security/privacy text explains consequences in one or two short sentences.
- Add a screenshot test for each new screen/state.
