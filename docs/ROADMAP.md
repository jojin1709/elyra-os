# Roadmap + exit criteria (a phase is done ONLY when all boxes pass)
## P0 Architecture
- [x] Master Architecture doc, repo layout, ADRs for device + Android version, coding standards, test strategy (Awaiting User Sign-off)
## P1 Boot
- [ ] Cuttlefish boots Elyra build AND reference device boots to home screen, 20 clean reboots
## P2 Hardware bring-up
- [ ] Display, touch, audio, Wi-Fi, BT, cellular, camera, sensors, USB, charging each PASS on real device (HARDWARE_STATUS.md)
## P3 System experience
- [ ] Launcher, SystemUI (status bar, QS, notifications, lock screen), Settings usable; UI screenshot tests green
## P4 Privacy & Security
- [ ] Privacy Center + Security Center show real data; SELinux enforcing; no neverallow violations
## P5 Freedom layer
- [ ] Documented unlock / relock-with-custom-key flow, root-compatible, fastboot/adb/recovery docs, security state banners
## P6 Performance
- [ ] Baseline vs after numbers for boot, RAM, launch time, battery, thermals
## P7 OTA & recovery
- [ ] Signed A/B OTA applies, rolls back on failure, failed-update test passes
## P8 Advanced
- [ ] Gaming mode, themes, optional AI (off by default), desktop mode
## P9 Daily-driver alpha
- [ ] 2 weeks real use, crash/battery report
## P10 Stable
- [ ] Extended real-device testing, security review, release notes
