#!/usr/bin/env python3
"""
Unit tests validating Elyra OS repository integrity, apps, SELinux policies, and product build configuration
"""

import os
import unittest

class TestElyraArchitectureIntegrity(unittest.TestCase):

    def setUp(self):
        self.root_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
        self.vendor_elyra = os.path.join(self.root_dir, "vendor", "elyra")

    def test_core_applications_exist(self):
        apps = [
            "ElyraSecurityCenter",
            "ElyraPrivacyCenter",
            "ElyraLauncher",
            "ElyraFiles",
            "ElyraUpdater",
            "ElyraSettings"
        ]
        apps_dir = os.path.join(self.vendor_elyra, "apps")
        for app in apps:
            app_path = os.path.join(apps_dir, app)
            self.assertTrue(os.path.isdir(app_path), f"App directory missing: {app}")
            self.assertTrue(os.path.exists(os.path.join(app_path, "AndroidManifest.xml")), f"{app} missing AndroidManifest.xml")
            self.assertTrue(os.path.exists(os.path.join(app_path, "Android.bp")), f"{app} missing Android.bp")
            self.assertTrue(os.path.exists(os.path.join(app_path, "build.gradle.kts")), f"{app} missing build.gradle.kts")

    def test_product_makefile_packages(self):
        mk_path = os.path.join(self.vendor_elyra, "elyra_common.mk")
        self.assertTrue(os.path.exists(mk_path), "elyra_common.mk must exist")
        with open(mk_path, "r", encoding="utf-8") as f:
            content = f.read()
        expected_packages = [
            "ElyraLauncher",
            "ElyraSettings",
            "ElyraFiles",
            "ElyraUpdater",
            "ElyraSecurityCenter",
            "ElyraPrivacyCenter",
            "ElyraFrameworkOverlay",
            "ElyraSystemUIOverlay",
            "org.elyra.security-aidl",
            "org.elyra.privacy-aidl"
        ]
        for pkg in expected_packages:
            self.assertIn(pkg, content, f"Package {pkg} missing from PRODUCT_PACKAGES in elyra_common.mk")

    def test_selinux_policies_exist(self):
        sepolicy_dir = os.path.join(self.vendor_elyra, "sepolicy", "private")
        expected_policies = [
            "elyra_security_center.te",
            "elyra_privacy_center.te",
            "elyra_launcher.te",
            "elyra_files.te",
            "elyra_updater.te",
            "elyra_settings.te",
            "service_contexts"
        ]
        for pol in expected_policies:
            pol_path = os.path.join(sepolicy_dir, pol)
            self.assertTrue(os.path.exists(pol_path), f"SELinux policy file missing: {pol}")

    def test_governance_and_docs_exist(self):
        docs_dir = os.path.join(self.root_dir, "docs")
        self.assertTrue(os.path.exists(os.path.join(docs_dir, "ARCHITECTURE.md")))
        self.assertTrue(os.path.exists(os.path.join(docs_dir, "ELYRA_SPEC.md")))
        self.assertTrue(os.path.exists(os.path.join(docs_dir, "STATE.md")))
        self.assertTrue(os.path.exists(os.path.join(docs_dir, "ROADMAP.md")))
        self.assertTrue(os.path.exists(os.path.join(docs_dir, "adr", "0001-reference-device.md")))
        self.assertTrue(os.path.exists(os.path.join(docs_dir, "adr", "0002-target-android-version.md")))
        self.assertTrue(os.path.exists(os.path.join(self.root_dir, "LICENSE")))
        self.assertTrue(os.path.exists(os.path.join(self.root_dir, "README.md")))

if __name__ == "__main__":
    unittest.main()
