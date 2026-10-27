#!/usr/bin/env bash
# usage: launch-time.sh <package/activity> [runs]  -> cold start TotalTime (ms), median
set -euo pipefail
comp="$1"; runs="${2:-10}"; pkg="${comp%%/*}"; t=()
for i in $(seq "$runs"); do
  adb shell am force-stop "$pkg"; sleep 1
  ms=$(adb shell am start -W -n "$comp" | awk '/TotalTime/{print $2}' | tr -d '\r')
  t+=("$ms"); sleep 2
done
printf '%s\n' "${t[@]}" | sort -n | awk '{a[NR]=$1} END{print "runs="NR" median_ms="a[int((NR+1)/2)]" min="a[1]" max="a[NR]}'
