#!/usr/bin/env bash
# Installs AOSP host build dependencies on Ubuntu/Debian + repo tool + cuttlefish prerequisites hint
set -euo pipefail
sudo apt-get update
sudo apt-get install -y git-core gnupg flex bison build-essential zip curl zlib1g-dev \
  libc6-dev-i386 x11proto-core-dev libx11-dev libgl1-mesa-dev libxml2-utils xsltproc unzip \
  fontconfig python3 python3-pip ccache rsync ripgrep adb fastboot libncurses5 lib32z1-dev || \
  echo "Some packages differ on your Ubuntu version; check source.android.com/docs/setup/start/requirements"
mkdir -p "$HOME/.bin"
if ! command -v repo >/dev/null; then
  curl -fsSL https://storage.googleapis.com/git-repo-downloads/repo -o "$HOME/.bin/repo"
  chmod a+x "$HOME/.bin/repo"
  grep -q '.bin' "$HOME/.bashrc" || echo 'export PATH="$HOME/.bin:$PATH"' >> "$HOME/.bashrc"
fi
git config --global user.name  >/dev/null || echo "Set: git config --global user.name 'Your Name'"
git config --global user.email >/dev/null || echo "Set: git config --global user.email 'you@example.com'"
echo "Host ready. Re-open the terminal so PATH updates."
