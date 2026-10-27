#!/usr/bin/env bash
# usage: meminfo.sh  -> total PSS summary + top 15 processes
adb shell dumpsys meminfo | sed -n '1,40p'
adb shell cat /proc/pressure/memory 2>/dev/null || true
