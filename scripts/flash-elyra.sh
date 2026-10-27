#!/usr/bin/env bash
# Elyra OS — Automated Fastboot Flashing Utility (Linux/macOS)
set -euo pipefail

echo "================================================="
echo "   ⚡ ELYRA OS FASTBOOT FLASHING UTILITY        "
echo "   Your device. Your rules.                     "
echo "================================================="

if ! command -v fastboot >/dev/null 2>&1; then
    echo "[-] Error: 'fastboot' tool is not found in PATH."
    echo "    Please install Android platform-tools before running."
    exit 1
fi

echo "[*] Checking for connected fastboot devices..."
DEVICE=$(fastboot devices | awk '{print $1}' | head -n 1)

if [ -z "$DEVICE" ]; then
    echo "[-] No fastboot device detected!"
    echo "    Please put your phone in fastboot mode (Hold Volume Down + Power) and connect USB."
    exit 1
fi

PRODUCT=$(fastboot getvar product 2>&1 | awk '/product:/ {print $2}')
CURRENT_SLOT=$(fastboot getvar current-slot 2>&1 | awk '/current-slot:/ {print $2}')

echo "[+] Device Serial : $DEVICE"
echo "[+] Target Product: $PRODUCT"
echo "[+] Active Slot   : $CURRENT_SLOT"

if [ "$PRODUCT" != "panther" ] && [ "$PRODUCT" != "lynx" ] && [ "$PRODUCT" != "shiba" ]; then
    echo "[!] Warning: Detected product is '$PRODUCT'."
    echo "    Official Elyra reference targets are panther (Pixel 7), lynx (Pixel 7a), and shiba (Pixel 8)."
    read -p "    Continue anyway? (y/N): " CONFIRM
    if [ "$CONFIRM" != "y" ] && [ "$CONFIRM" != "Y" ]; then
        echo "Aborted."
        exit 0
    fi
fi

echo ""
echo "[!] CAUTION: Flashing a new OS will wipe all user data on the device."
read -p "Proceed with flashing Elyra OS? (y/N): " RUN_FLASH

if [ "$RUN_FLASH" != "y" ] && [ "$RUN_FLASH" != "Y" ]; then
    echo "Flashing canceled."
    exit 0
fi

echo "[*] Flashing bootloader & radio firmware..."
[ -f "bootloader.img" ] && fastboot flash bootloader bootloader.img
[ -f "radio.img" ] && fastboot flash radio radio.img

echo "[*] Rebooting bootloader to apply firmware..."
fastboot reboot-bootloader
sleep 3

echo "[*] Flashing core boot partitions..."
[ -f "boot.img" ] && fastboot flash boot boot.img
[ -f "init_boot.img" ] && fastboot flash init_boot init_boot.img
[ -f "dtbo.img" ] && fastboot flash dtbo dtbo.img
[ -f "vendor_kernel_boot.img" ] && fastboot flash vendor_kernel_boot vendor_kernel_boot.img
[ -f "vbmeta.img" ] && fastboot flash vbmeta vbmeta.img

echo "[*] Rebooting into fastbootd (userspace dynamic partition mode)..."
fastboot reboot fastboot
sleep 5

echo "[*] Flashing super image (system, vendor, product, system_ext)..."
[ -f "super.img" ] && fastboot flash super super.img

echo "[*] Wiping user data for clean first boot..."
fastboot -w

echo ""
echo "================================================="
echo "[+] Flashing complete! Rebooting into Elyra OS..."
echo "================================================="
fastboot reboot
