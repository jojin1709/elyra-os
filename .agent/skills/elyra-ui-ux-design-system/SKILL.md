---
name: elyra-ui-ux-design-system
description: Elyra OS visual language, design tokens, components, motion and accessibility rules. Use before designing or building ANY visible UI.
---
# Elyra design system v1
Feel: calm, precise, fast, premium. Think "shippable flagship phone", not concept art.
## Principles
1. Clarity over decoration. 2. One primary action per screen. 3. Motion explains, never decorates. 4. Defaults are good, depth is available. 5. Honest status: security/privacy state is always readable at a glance.
## Tokens (single source: `vendor/elyra/design/tokens.json`, generated to Kotlin + XML)
- Color roles: surface, surfaceVariant, onSurface, primary, onPrimary, secondary, error, warning, success, outline. Dynamic accent from user color/wallpaper (Material You style tonal palette) with a fixed default accent of calm indigo-blue.
- Themes: Light, Dark (soft near-black), AMOLED (true #000000 surfaces, no gray cards).
- Type: one family (e.g. Inter or Roboto Flex, OFL-licensed). Scale: Display 36, Title 22, Subtitle 17, Body 15, Label 13, Caption 12 (sp). Weights 400/500/600 only.
- Spacing: 4dp base, steps 4/8/12/16/24/32/48. Screen padding 20dp.
- Radius: 8 small, 16 card, 28 sheet/dialog, full for chips/switch.
- Elevation: prefer tonal surface shift over shadows. Max 2 shadow levels.
- Icons: one rounded-outline set, 24dp, 2dp stroke, consistent optical size.
- Motion: durations 100/200/300ms, easing: standard (0.2,0,0,1), emphasized decel for enter, accelerate for exit. Springs for gestures. Respect "remove animations".
- Haptics: tick on toggles/detents, confirm on success, none on scroll.
## Components to build once and reuse
Top bar, list row (icon/title/summary/trailing), card, switch/slider/segmented, chips, bottom sheet, dialog, banner (info/warning/danger), empty state, skeleton loader, toast, status pill (e.g. "Bootloader unlocked").
## Signature touches (keep subtle)
Smooth gesture-driven transitions, tonal depth, generous spacing, clean typography, one accent. No neon, no heavy blur/glass, no skeuomorphic gloss.
## Accessibility (non-negotiable)
Contrast 4.5:1 text / 3:1 UI, 48dp targets, font scale to 200%, TalkBack labels + order, color never the only signal, RTL, reduced motion, one-handed reachability (key actions in lower half).
## Copy
Short, direct, human. Explain consequences: "Your bootloader is unlocked. Apps that need a locked device may not work." No jargon without a one-line explanation.
## Process
Spec first (`docs/design/<screen>.md`: purpose, layout, states, tokens used), then build, then screenshot review against this file.
