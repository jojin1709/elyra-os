---
name: aosp-build-env
description: Set up and run reproducible AOSP builds on Ubuntu/Debian (repo sync, lunch, m, Soong, ccache). Use for any build environment, sync or build failure task.
---
# AOSP build environment
## Host
- Ubuntu LTS 64-bit, 64GB+ RAM ideal (32GB minimum + swap), 400GB+ free SSD, 16+ cores helps.
- Packages and `repo` tool: run `scripts/setup-host.sh`. JDK/Clang/Rust come from AOSP prebuilts, don't install your own for the platform build.
## Source
```bash
export ELYRA_AOSP=${ELYRA_AOSP:-$HOME/elyra-aosp}
mkdir -p $ELYRA_AOSP && cd $ELYRA_AOSP
repo init -u https://android.googlesource.com/platform/manifest -b <BRANCH> --depth=1 --partial-clone
repo sync -c -j8 --no-tags --optimized-fetch
```
- `<BRANCH>` comes from the ADR for the reference device. Verify it on source.android.com/docs/setup/reference/build-numbers first, never guess.
- Elyra additions come via a local manifest in `.repo/local_manifests/elyra.xml` (generated from this repo's `manifest/`).
## Build
```bash
source build/envsetup.sh
lunch <target>        # run `lunch` with no args to list targets for THIS branch; don't assume names
export USE_CCACHE=1 CCACHE_EXEC=$(which ccache); ccache -M 100G
m -j$(nproc) 2>&1 | tee $ELYRA_REPO/logs/build-$(date +%F-%H%M).log
```
- Incremental: re-run `m`. Single module: `mm`, `mmm path`, or `m <module>`.
- Failures: read the FIRST error in the log (`rg -n "FAILED|error:" log | head`). Soong errors -> Android.bp; SELinux -> `*.te` / neverallow; linker -> missing shared_libs.
- Reproducibility: pin manifest revisions with `repo manifest -r -o manifest/pinned.xml` per release.
- Software target for fast iteration: Cuttlefish (see skill `testing-cuttlefish`).
