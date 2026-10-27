---
description: Run a security review of the current tree and build
---
1. Load `security-engineer` + `selinux-avb-security`.
2. Check: SELinux enforcing, new denials, any permissive domains, neverallow changes, exported components of Elyra apps, privapp allowlists, keys not in repo (`git secrets`/grep), AVB flags, FBE enabled.
3. Run static analysis (clang-tidy, clippy, lint, detekt) and fuzzers for touched parsers.
4. Write `docs/security/audit-<date>.md`: findings (critical/high/med/low), evidence, fix status.
