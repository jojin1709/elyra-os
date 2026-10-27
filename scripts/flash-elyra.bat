@echo off
setlocal enabledelayedexpansion

echo =================================================
echo    ⚡ ELYRA OS FASTBOOT FLASHING UTILITY (WIN)   
echo    Your device. Your rules.                     
echo =================================================

where fastboot >nul 2>nul
if %ERRORLEVEL% neq 0 (
    echo [-] Error: 'fastboot' tool not found in PATH.
    echo     Please install Android platform-tools before running.
    pause
    exit /b 1
)

echo [*] Checking for connected fastboot devices...
fastboot devices
for /f "tokens=1" %%d in ('fastboot devices') do set DEVICE=%%d

if "%DEVICE%"=="" (
    echo [-] No fastboot device detected!
    echo     Put your phone in fastboot mode (Volume Down + Power) and connect USB.
    pause
    exit /b 1
)

echo [!] CAUTION: Flashing a new OS will wipe all user data on the device.
set /p CONFIRM="Proceed with flashing Elyra OS? (y/N): "
if /i not "%CONFIRM%"=="y" (
    echo Flashing canceled.
    exit /b 0
)

echo [*] Flashing core boot partitions...
if exist boot.img fastboot flash boot boot.img
if exist init_boot.img fastboot flash init_boot init_boot.img
if exist dtbo.img fastboot flash dtbo dtbo.img
if exist vendor_kernel_boot.img fastboot flash vendor_kernel_boot vendor_kernel_boot.img
if exist vbmeta.img fastboot flash vbmeta vbmeta.img

echo [*] Rebooting into fastbootd...
fastboot reboot fastboot
timeout /t 5 >nul

echo [*] Flashing dynamic partitions...
if exist super.img fastboot flash super super.img

echo [*] Wiping userdata...
fastboot -w

echo =================================================
echo [+] Flashing complete! Rebooting into Elyra OS...
echo =================================================
fastboot reboot
pause
