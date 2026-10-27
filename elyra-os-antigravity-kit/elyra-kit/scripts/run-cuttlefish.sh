#!/usr/bin/env bash
# Boot the current build on Cuttlefish (run inside a lunch'd shell in $ELYRA_AOSP)
set -euo pipefail
command -v launch_cvd >/dev/null || { echo "Run: source build/envsetup.sh && lunch <cf target>, then build, first."; exit 1; }
launch_cvd --daemon --resume=false
sleep 5; adb wait-for-device
adb shell getprop sys.boot_completed
echo "Open the display: https://localhost:8443 (WebRTC) or run 'cvd status'"
