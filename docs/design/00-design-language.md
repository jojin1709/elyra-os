# Elyra OS — Design Language Specification & Screen Catalog (v1.0)

## 1. Aesthetic Vision & Foundational Philosophy
Elyra OS is designed for human calm, tactile precision, and instant responsiveness. The visual aesthetic reflects a polished flagship smartphone operating system—quietly confident, highly legible, and free from unnecessary ornament.

### Core Principles
1. **Clarity Over Decoration**: Every visual element serves an informational or navigational purpose. No heavy glass blurs, neon gradients, or artificial skeuomorphism.
2. **One Primary Action per Screen**: Visual hierarchy guides the user's eye to the primary task effortlessly.
3. **Motion Explains, Never Distracts**: Micro-animations convey spatial origin, hierarchy, and system state. Animations never delay input processing.
4. **Honest System Transparency**: Security, privacy, and device status are communicated in plain language with truthful indicators.
5. **Universal Accessibility**: Built from day one for 4.5:1 minimum text contrast, 48dp touch targets, TalkBack screen readers, and dynamic font scaling up to 200%.

---

## 2. Design Tokens Architecture

### Color System (Tonal Palette & Theme Matrix)

| Token Name | Light Theme | Soft Dark Theme | AMOLED Theme | Semantic Meaning |
| :--- | :--- | :--- | :--- | :--- |
| `surface` | `#FFFFFF` | `#121417` | `#000000` | Base background for windows and pages |
| `surfaceVariant` | `#F1F3F5` | `#1B1E22` | `#0D0E10` | Subtle background for cards and containers |
| `onSurface` | `#1A1D20` | `#E8EAED` | `#F1F3F4` | Primary high-contrast text and glyphs |
| `onSurfaceVariant`| `#5F6368` | `#9AA0A6` | `#9AA0A6` | Secondary labels, summaries, captions |
| `primary` | `#2D5BFF` | `#4B73FF` | `#4B73FF` | Primary brand accent (Calm Indigo) |
| `onPrimary` | `#FFFFFF` | `#FFFFFF` | `#FFFFFF` | Text/icons on primary colored surfaces |
| `secondary` | `#4A5568` | `#A0AEC0` | `#CBD5E0` | Supporting interactive controls |
| `outline` | `#E2E8F0` | `#2D3748` | `#26292E` | Card borders, dividers, subtle separators |
| `success` | `#10B981` | `#34D399` | `#34D399` | Safe status, verified boot green, secure |
| `warning` | `#F59E0B` | `#FBBF24` | `#FBBF24` | Unlocked bootloader, developer mode |
| `error` | `#EF4444` | `#F87171` | `#F87171` | AVB red state, critical permission alerts |

### Typography Scale (Family: Inter / Roboto Flex)
- **Display**: 36sp / Line Height 44sp / Weight: Semi-Bold (600) — Hero headings and clock displays.
- **Title**: 22sp / Line Height 28sp / Weight: Medium (500) — Top bar titles, dialog headers.
- **Subtitle**: 17sp / Line Height 24sp / Weight: Medium (500) — Card titles, section headers.
- **Body**: 15sp / Line Height 22sp / Weight: Regular (400) — Primary list items, content body.
- **Label**: 13sp / Line Height 18sp / Weight: Medium (500) — Button labels, Quick Settings tiles.
- **Caption**: 12sp / Line Height 16sp / Weight: Regular (400) — Metadata, timestamps, helper copy.

### Spatial Scale & Geometry
- **Base Grid**: 4dp baseline. Steps: 4dp, 8dp, 12dp, 16dp, 20dp (Standard Screen Margin), 24dp, 32dp, 48dp.
- **Corner Radii**:
  - Small (8dp): Chips, tags, small buttons.
  - Card (16dp): Content cards, preference groups, Quick Settings tiles.
  - Sheet/Dialog (28dp): Bottom sheets, modal dialogs, alert sheets.
  - Pill (9999dp): Toggle switches, primary pill buttons, status badges.
- **Elevation**: Prefer tonal surface shifts over heavy drop shadows. Maximum 2 shadow levels for elevated dialogs and sheets.

### Motion & Haptics
- **Durations**:
  - Fast (100ms): Button state changes, toggle flips.
  - Normal (200ms): Expansion, collapse, card selection.
  - Emphasized (300ms): Activity entry, sheet transitions, full-screen navigations.
- **Curves**: Standard Easing `cubic-bezier(0.2, 0.0, 0.0, 1.0)`.
- **Haptic Feedback**: Subtle physical clicks on switch detents and tile presses; solid confirmation buzz on security approvals; zero haptics during continuous scroll.

---

## 3. Phase 3 System Screen Catalog (12 Core Screens)

### 1. Elyra Launcher (Home Screen & App Drawer)
- **Purpose**: Fast, distraction-free gateway to applications and widgets.
- **Layout**: 4x5 or 5x5 grid with configurable icon sizing; clean search dock at bottom; smooth upward swipe for alphabetized App Drawer.
- **Signature Feature**: Zero algorithmic feeds, zero ads, instant cold launch (<400ms), long-press shortcuts with direct access to Elyra Privacy & Security settings.

### 2. Status Bar
- **Purpose**: Persistent, glanceable system health and notification indicator.
- **Layout**: Clean left-aligned clock; center display camera cutout awareness; right-aligned signal strength, battery pill with internal percentage, and active privacy dots (green dot for camera/mic, blue dot for location).
- **Behavior**: Smooth fade transition during full-screen media playback; insets strictly conform to Android 15 edge-to-edge guidelines.

### 3. Quick Settings (QS) Panel
- **Purpose**: Rapid access to device toggles, connectivity, and system states.
- **Layout**: 2x4 card grid featuring rounded-pill tiles (Internet, Bluetooth, Privacy Guard, Camera Kill-switch, Mic Kill-switch, Dark Theme, Hotspot, Flashlight).
- **Interactivity**: Smooth drag gesture from status bar; one-tap toggle, long-press jumps directly to the corresponding Settings sub-screen.

### 4. Notification Shade
- **Purpose**: Clean, grouped, and actionable notification management.
- **Layout**: Integrated below Quick Settings; categorized into Conversations, Alerting, and Silent groups; cards feature 16dp radius with subtle outline.
- **Interactivity**: Horizontal swipe with spring resistance to dismiss; quick-reply inline text input; one-tap "Clear All" pill at bottom right.

### 5. Lock Screen & Keyguard
- **Purpose**: Secure authentication barrier and ambient information glance.
- **Layout**: Centered minimal typography clock (Display 36sp); ambient notifications below clock; bottom left emergency/camera shortcuts; centered optical fingerprint target with subtle feedback ring.
- **Security Indicator**: Unobtrusive status pill at top: "Verified Boot: Secure" or "Bootloader Unlocked" (amber).

### 6. Settings Home
- **Purpose**: Organized configuration center for all hardware and system preferences.
- **Layout**: Generous top header ("Settings"); searchable search bar; grouped preference cards with 16dp corner radius and left-aligned rounded icons.
- **Structure**:
  - Top Group: Network & Internet, Connected Devices.
  - Focus Group: Elyra Experience, Display & AMOLED, Sound & Vibration.
  - Core Trust Group: Privacy Center, Security Center.
  - System Group: Battery & Thermals, Apps, System & Updater, About Phone.

### 7. Elyra Privacy Center
- **Purpose**: Centralized dashboard for permissions, sensor activity, and data access.
- **Layout**:
  - Top Banner: 24-hour sensor access summary (Camera, Microphone, Location).
  - Quick Toggles: System-wide hardware kill-switches for Camera and Microphone.
  - Permission Timeline: Chronological audit log showing exact app access times.
  - App Permission Manager: One-tap revocations per application.

### 8. Elyra Security Center
- **Purpose**: Honest, comprehensive evaluation of physical and software security state.
- **Layout**:
  - Overall Status Grade: Clear visual badge (Green: Fully Verified & Encrypted; Amber: Unlocked / Custom Keys; Red: Tampered).
  - Metrics Cards:
    1. Verified Boot State (`ro.boot.verifiedbootstate`: green, yellow, orange).
    2. File-Based Encryption (FBE) status & Keymaster status.
    3. SELinux Mode (Always Enforcing).
    4. Android Security Patch Level & Elyra Build ID.
    5. Developer Options & USB Debugging status.
- **Copy**: Plain human language explaining the real consequences of each state without alarmist messaging.

### 9. Elyra Updater (A/B Seamless Client)
- **Purpose**: Reliable background OS updates with automated fallback rollback protection.
- **Layout**: Clean current version card; "Check for updates" primary pill button; changelog accordion; progress bar during payload verification and streaming A/B installation.
- **Safety Feature**: Explicit prompt before reboot confirming battery level (>50%) and verifying cryptographic signature.

### 10. Elyra Files (DocumentsUI)
- **Purpose**: Modern, fast, and secure local file manager.
- **Layout**: Top storage breakdown (Internal Storage, USB-OTG); quick category chips (Images, Videos, Audio, Documents, Downloads); grid or list view with fast thumbnail caching.
- **Privacy Focus**: Built-in Private Vault folder protected by biometric authentication.

### 11. Power Menu
- **Purpose**: Safe power-down, restart, recovery, and emergency access.
- **Layout**: Floating bottom-aligned card with large, distinct circular actions: Power Off, Restart, Advanced Reboot (Recovery, Fastboot, Bootloader), Emergency SOS.
- **Safety**: Prevents accidental touches with confirmation haptics.

### 12. Volume Dialog
- **Purpose**: Tactile audio stream management.
- **Layout**: Vertical slider pill appearing adjacent to the physical volume rocker; smooth spring expansion on drag; expandable sheet revealing Media, Call, Ring, and Notification streams.
- **Toggles**: Single-tap sound profile switcher (Ring, Vibrate, Silent).
