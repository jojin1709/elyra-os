---
trigger: always_on
---
# Security rules
- Keep SELinux enforcing in every user/userdebug build we ship. Permissive only in a clearly labeled local debug build.
- Never edit `neverallow` rules to silence a denial. Fix the policy properly (least privilege).
- Keep AVB + dm-verity + FBE on. Custom signing keys live OUTSIDE the repo (`$ELYRA_KEYS`), never committed.
- Unlocked/rooted state must be DETECTED and SHOWN in Security Center (`ro.boot.verifiedbootstate`, `ro.boot.flash.locked`, `ro.boot.vbmeta.device_state`). Never hide it, never add root-hiding.
- New privileged app = explicit allowlist entry + permission justification in an ADR.
- Security-sensitive native code: prefer Rust; run fuzzers (`cc_fuzz`/`rust_fuzz`) before merge.
