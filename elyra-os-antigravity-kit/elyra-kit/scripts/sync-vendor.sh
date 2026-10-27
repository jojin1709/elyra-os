#!/usr/bin/env bash
# Copy vendor/elyra from this repo into the AOSP tree
set -euo pipefail
: "${ELYRA_AOSP:=$HOME/elyra-aosp}"
HERE="$(cd "$(dirname "$0")/.." && pwd)"
mkdir -p "$ELYRA_AOSP/vendor/elyra"
rsync -a --delete "$HERE/vendor/elyra/" "$ELYRA_AOSP/vendor/elyra/"
echo "Synced vendor/elyra -> $ELYRA_AOSP/vendor/elyra"
