# Elyra OS workspace (Antigravity kit)
Quick start:
```bash
unzip elyra-os-antigravity-kit.zip && cd elyra-kit
./setup.sh --community            # --awesome for the huge skill catalog, --host-deps to install AOSP build deps, --global to also install Elyra skills for all projects
cp /path/to/your/spec.md docs/ELYRA_SPEC.md
```
Open the folder in Antigravity, paste `MASTER_PROMPT.md`. Then drive phases with slash workflows in `.agent/workflows/`.

What's inside
- `GEMINI.md`: short always-on identity + routing (kept small so a Flash model stays sharp)
- `.agent/rules/`: security, engineering, ui-ux, testing
- `.agent/agents/`: 7 specialist roles (architect, kernel, UI engineer, UX designer, security, performance, QA/release)
- `.agent/skills/`: 11 Elyra skills (AOSP build, repo layout, services, bring-up, SystemUI, design system, SELinux/AVB, freedom layer, perf, testing, OTA)
- `.agent/workflows/`: 9 slash commands
- `docs/ROADMAP.md` + `docs/STATE.md`: phase gates + live memory the agent updates every session
- `scripts/`: host setup, vendor sync, Cuttlefish, perf collectors

Machine reality check: a full AOSP build wants 32-64GB RAM and 400GB+ SSD. No hardware = Phases 0, 3 (UI on Cuttlefish), 4 still work.
