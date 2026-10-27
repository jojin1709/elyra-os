#!/usr/bin/env bash
# Prints host capability for AOSP builds
echo "CPU cores : $(nproc)"
echo "RAM       : $(free -g | awk '/Mem:/{print $2" GB"}')   swap: $(free -g | awk '/Swap:/{print $2" GB"}')"
echo "Disk free : $(df -h --output=avail "${ELYRA_AOSP:-$HOME}" 2>/dev/null | tail -1) (need 400GB+)"
. /etc/os-release 2>/dev/null && echo "OS        : $PRETTY_NAME"
free_gb=$(df -BG --output=avail "${ELYRA_AOSP:-$HOME}" | tail -1 | tr -dc 0-9)
ram_gb=$(free -g | awk '/Mem:/{print $2}')
[ "${free_gb:-0}" -lt 400 ] && echo "WARN: less than 400GB free disk"
[ "${ram_gb:-0}" -lt 30 ] && echo "WARN: under 32GB RAM, add big swap and use fewer jobs"
exit 0
