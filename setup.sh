#!/usr/bin/env bash
# Elyra OS workspace bootstrap for Google Antigravity
# usage: ./setup.sh [--community] [--awesome] [--host-deps] [--global]
set -euo pipefail
HERE="$(cd "$(dirname "$0")" && pwd)"; cd "$HERE"
COMMUNITY=0; AWESOME=0; HOST=0; GLOBAL=0
for a in "$@"; do case $a in
  --community) COMMUNITY=1;; --awesome) AWESOME=1;; --host-deps) HOST=1;; --global) GLOBAL=1;;
  *) echo "unknown flag $a"; exit 1;; esac; done

echo "==> Git repo"
[ -d .git ] || { git init -q && git add -A && git commit -qm "chore: elyra workspace bootstrap" || true; }

echo "==> Newer Antigravity reads .agents/ : linking to .agent/"
[ -e .agents ] || ln -s .agent .agents

echo "==> Env file"
cat > .env.elyra <<EOT
export ELYRA_REPO="$HERE"
export ELYRA_AOSP="\${ELYRA_AOSP:-\$HOME/elyra-aosp}"
export ELYRA_KEYS="\${ELYRA_KEYS:-\$HOME/.elyra-keys}"   # release signing keys, NEVER commit
EOT
mkdir -p "$HOME/.elyra-keys" && chmod 700 "$HOME/.elyra-keys"
grep -q "elyra-workspace" "$HOME/.bashrc" 2>/dev/null || echo "[ -f \"$HERE/.env.elyra\" ] && . \"$HERE/.env.elyra\"  # elyra-workspace" >> "$HOME/.bashrc"

if [ $COMMUNITY -eq 1 ]; then
  echo "==> Downloading community Antigravity skills (rominirani/antigravity-skills)"
  tmp=$(mktemp -d); git clone --depth=1 https://github.com/rominirani/antigravity-skills.git "$tmp/rs"
  find "$tmp/rs" -name SKILL.md -printf '%h\n' | while read -r d; do
    n="community-$(basename "$d")"; rm -rf ".agent/skills/$n"; cp -r "$d" ".agent/skills/$n"; echo "   + $n"
  done; rm -rf "$tmp"
fi

if [ $AWESOME -eq 1 ]; then
  echo "==> Installing antigravity-awesome-skills (HUGE catalog, uses more tokens on every task)"
  npx --yes antigravity-awesome-skills --path "$HERE/.agent/skills"
fi

if [ $GLOBAL -eq 1 ]; then
  echo "==> Also installing Elyra skills globally (~/.gemini/antigravity/skills)"
  mkdir -p "$HOME/.gemini/antigravity/skills"; cp -r .agent/skills/elyra-* "$HOME/.gemini/antigravity/skills/"
fi

if [ $HOST -eq 1 ]; then ./scripts/setup-host.sh; fi
./scripts/check-host.sh || true

cat <<EOT

DONE. Next:
 1) Put your ORIGINAL Elyra spec in docs/ELYRA_SPEC.md
 2) Open this folder in Antigravity (File > Open Folder)
 3) Open MASTER_PROMPT.md, paste it into the agent panel (use the best/planning model for Phase 0)
 4) Later phases: type /setup-host, /build-aosp, /bringup-device, /ui-review ... (see .agent/workflows)
EOT
