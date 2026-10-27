# Elyra OS — Repository Structure & Build Environment Plan

## 1. Repository Layout Specification
Following the `elyra-repo-layout` architecture, the Elyra ecosystem is divided into host workspace and external AOSP tree:

```
d:/custom os/ (elyra-workspace)
├── .agent/ / .agents/          # Antigravity agents, skills, workflows, rules
│   ├── agents/                 # 7 specialist roles (architect, kernel, UI, UX, sec, perf, QA)
│   ├── rules/                  # engineering, security, testing, ui-ux
│   ├── skills/                 # 11 Elyra system engineering skills
│   └── workflows/              # 9 slash command execution playbooks
├── manifest/                   # repo manifest overlays and pinned manifests
│   ├── default.xml             # local_manifest pinning for AOSP
│   └── pinned.xml              # exact SHA-256 reproducible build manifests
├── vendor/elyra/               # Core Elyra code synced into $ELYRA_AOSP/vendor/elyra
│   ├── elyra_common.mk         # Core build inheritance, system properties, core packages
│   ├── elyra_optional.mk       # Optional modular packages (MicroG, AI, gaming)
│   ├── overlays/               # RRO overlays (framework-res, SystemUI, Settings, Launcher)
│   ├── sepolicy/               # SELinux security policies (public, private, vendor)
│   ├── apps/                   # First-party apps (ElyraLauncher, ElyraSettings, etc.)
│   ├── services/               # Elyra system services and AIDL IPC definitions
│   └── design/                 # Single-source tokens.json and generated styles
├── patches/                    # Documented, rebased patches against upstream AOSP
├── scripts/                    # Automation scripts (setup-host, run-cuttlefish, check-host)
├── docs/                       # Architecture, ADRs, design specs, roadmaps, live state
└── logs/                       # Build and test execution logs
```

### External AOSP Tree
The primary AOSP source checkout resides outside this repository at `$ELYRA_AOSP` (e.g. `/home/user/elyra-aosp` or Linux build volume).
- Local manifests link `vendor/elyra` directly into `$ELYRA_AOSP/vendor/elyra`.
- Developers run searches across `$ELYRA_AOSP` using `rg` from the terminal and never open the 400GB+ tree in the IDE workspace.

---

## 2. Build Environment & Host Specifications

### Hardware Requirements
- **Host OS**: Ubuntu 22.04 LTS / 24.04 LTS (x86_64) or WSL2 with Ubuntu.
- **CPU**: 16+ virtual cores recommended (Minimum 8 cores).
- **RAM**: 32 GB minimum (64 GB recommended) + 32 GB swap file.
- **Storage**: 400 GB+ free NVMe SSD space (for AOSP source, ccache, and target out directories).

### Toolchain & Dependencies
- Prebuilts managed by AOSP: Clang/LLVM 18+, Rust 1.77+, OpenJDK 21, Python 3.10+.
- Host utilities: `ccache`, `repo`, `git`, `libncurses5`, `zip`, `unzip`, `curl`, `rsync`.
- Ccache allocation: 100 GB cache on fast SSD via `export USE_CCACHE=1 && ccache -M 100G`.

### Build Commands
```bash
# Initialize build shell
source build/envsetup.sh

# Select Lunch Target
lunch aosp_panther-userdebug      # Reference Pixel 7 hardware
# OR
lunch aosp_cf_x86_64_phone-userdebug # Cuttlefish Emulator

# Execute Build with logging
m -j$(nproc) 2>&1 | tee $ELYRA_REPO/logs/build-$(date +%F-%H%M).log
```
