#!/usr/bin/env python3
"""
Unit tests for Elyra OS Design Token validation
"""

import json
import os
import re
import unittest

class TestElyraDesignTokens(unittest.TestCase):

    def setUp(self):
        self.root_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
        self.tokens_path = os.path.join(self.root_dir, "vendor", "elyra", "design", "tokens.json")
        self.assertTrue(os.path.exists(self.tokens_path), "tokens.json must exist")
        with open(self.tokens_path, "r", encoding="utf-8") as f:
            self.tokens = json.load(f)

    def test_schema_keys(self):
        required_keys = ["colors", "typography", "spacing", "radii", "motion"]
        for key in required_keys:
            self.assertIn(key, self.tokens, f"Missing required token key: {key}")

    def test_color_hex_formats(self):
        hex_pattern = re.compile(r"^#[0-9a-fA-F]{6}$")
        for theme in ["light", "dark", "amoled"]:
            self.assertIn(theme, self.tokens["colors"])
            theme_colors = self.tokens["colors"][theme]
            self.assertIn("surface", theme_colors)
            self.assertIn("primary", theme_colors)
            self.assertIn("onSurface", theme_colors)
            for color_name, hex_val in theme_colors.items():
                self.assertTrue(
                    hex_pattern.match(hex_val),
                    f"Color {color_name} in theme {theme} has invalid hex: {hex_val}"
                )

    def test_amoled_black_invariant(self):
        # AMOLED theme surface MUST be true #000000
        amoled_surface = self.tokens["colors"]["amoled"]["surface"]
        self.assertEqual(amoled_surface.upper(), "#000000", "AMOLED theme surface must be true pure #000000")

    def test_calm_indigo_primary_invariant(self):
        light_primary = self.tokens["colors"]["light"]["primary"].upper()
        self.assertEqual(light_primary, "#2D5BFF", "Light theme primary must be Calm Indigo #2D5BFF")

    def test_generated_xml_outputs(self):
        gen_dir = os.path.join(self.root_dir, "vendor", "elyra", "design", "generated")
        self.assertTrue(os.path.exists(os.path.join(gen_dir, "res", "values", "colors.xml")))
        self.assertTrue(os.path.exists(os.path.join(gen_dir, "res", "values-night", "colors.xml")))
        self.assertTrue(os.path.exists(os.path.join(gen_dir, "res", "values", "dimens.xml")))
        self.assertTrue(os.path.exists(os.path.join(gen_dir, "kotlin", "org", "elyra", "theme", "ElyraTheme.kt")))

if __name__ == "__main__":
    unittest.main()
