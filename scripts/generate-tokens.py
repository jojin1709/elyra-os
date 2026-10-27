#!/usr/bin/env python3
"""
Elyra OS — Design Token Generator
Translates vendor/elyra/design/tokens.json into:
1. Android Resource XMLs (colors, dimens, styles) for Light, Dark, and AMOLED
2. Kotlin Jetpack Compose Design System (Colors, Typography, Shapes, Theme)
"""

import json
import os
import sys

def main():
    root_dir = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
    tokens_path = os.path.join(root_dir, "vendor", "elyra", "design", "tokens.json")
    
    if not os.path.exists(tokens_path):
        print(f"Error: tokens.json not found at {tokens_path}")
        sys.exit(1)
        
    with open(tokens_path, "r", encoding="utf-8") as f:
        tokens = json.load(f)
        
    out_dir = os.path.join(root_dir, "vendor", "elyra", "design", "generated")
    res_dir = os.path.join(out_dir, "res")
    kotlin_dir = os.path.join(out_dir, "kotlin", "org", "elyra", "theme")
    
    os.makedirs(os.path.join(res_dir, "values"), exist_ok=True)
    os.makedirs(os.path.join(res_dir, "values-night"), exist_ok=True)
    os.makedirs(kotlin_dir, exist_ok=True)
    
    colors = tokens["colors"]
    
    # 1. Generate values/colors.xml (Light theme)
    with open(os.path.join(res_dir, "values", "colors.xml"), "w", encoding="utf-8") as f:
        f.write('<?xml version="1.0" encoding="utf-8"?>\n')
        f.write('<!-- Auto-generated from tokens.json. Do not edit directly. -->\n')
        f.write('<resources>\n')
        for k, v in colors["light"].items():
            f.write(f'    <color name="elyra_{k}">{v}</color>\n')
        f.write('</resources>\n')

    # 2. Generate values-night/colors.xml (Dark theme)
    with open(os.path.join(res_dir, "values-night", "colors.xml"), "w", encoding="utf-8") as f:
        f.write('<?xml version="1.0" encoding="utf-8"?>\n')
        f.write('<!-- Auto-generated from tokens.json. Do not edit directly. -->\n')
        f.write('<resources>\n')
        for k, v in colors["dark"].items():
            f.write(f'    <color name="elyra_{k}">{v}</color>\n')
        f.write('</resources>\n')
        
    # 3. Generate values/dimens.xml
    with open(os.path.join(res_dir, "values", "dimens.xml"), "w", encoding="utf-8") as f:
        f.write('<?xml version="1.0" encoding="utf-8"?>\n')
        f.write('<!-- Auto-generated from tokens.json. Do not edit directly. -->\n')
        f.write('<resources>\n')
        for sp in tokens["spacing"]["scale"]:
            f.write(f'    <dimen name="elyra_spacing_{sp}dp">{sp}dp</dimen>\n')
        for k, v in tokens["radii"].items():
            f.write(f'    <dimen name="elyra_radius_{k}">{v}dp</dimen>\n')
        f.write('</resources>\n')

    # 4. Generate Kotlin Compose Colors (Color.kt)
    with open(os.path.join(kotlin_dir, "Color.kt"), "w", encoding="utf-8") as f:
        f.write('package org.elyra.theme\n\n')
        f.write('import androidx.compose.ui.graphics.Color\n\n')
        f.write('// Auto-generated from tokens.json. Do not edit directly.\n')
        for theme_name, theme_colors in colors.items():
            f.write(f'// {theme_name.capitalize()} Theme Colors\n')
            for k, v in theme_colors.items():
                hex_val = v.lstrip('#')
                f.write(f'val Elyra{theme_name.capitalize()}_{k.capitalize()} = Color(0xFF{hex_val})\n')
            f.write('\n')

    # 5. Generate Kotlin Compose Theme (ElyraTheme.kt)
    with open(os.path.join(kotlin_dir, "ElyraTheme.kt"), "w", encoding="utf-8") as f:
        f.write('''package org.elyra.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Immutable
data class ElyraExtendedColors(
    val surface: Color,
    val surfaceVariant: Color,
    val onSurface: Color,
    val onSurfaceVariant: Color,
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val outline: Color,
    val success: Color,
    val warning: Color,
    val error: Color,
    val isAmoled: Boolean = false
)

val LocalElyraColors = staticCompositionLocalOf {
    ElyraExtendedColors(
        surface = ElyraLight_Surface,
        surfaceVariant = ElyraLight_Surfacevariant,
        onSurface = ElyraLight_Onsurface,
        onSurfaceVariant = ElyraLight_Onsurfacevariant,
        primary = ElyraLight_Primary,
        onPrimary = ElyraLight_Onprimary,
        secondary = ElyraLight_Secondary,
        outline = ElyraLight_Outline,
        success = ElyraLight_Success,
        warning = ElyraLight_Warning,
        error = ElyraLight_Error
    )
}

val ElyraShapes = Shapes(
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(28.dp),
    extraLarge = RoundedCornerShape(9999.dp)
)

@Composable
fun ElyraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    amoled: Boolean = false,
    content: @Composable () -> Unit
) {
    val extendedColors = when {
        amoled -> ElyraExtendedColors(
            surface = ElyraAmoled_Surface,
            surfaceVariant = ElyraAmoled_Surfacevariant,
            onSurface = ElyraAmoled_Onsurface,
            onSurfaceVariant = ElyraAmoled_Onsurfacevariant,
            primary = ElyraAmoled_Primary,
            onPrimary = ElyraAmoled_Onprimary,
            secondary = ElyraAmoled_Secondary,
            outline = ElyraAmoled_Outline,
            success = ElyraAmoled_Success,
            warning = ElyraAmoled_Warning,
            error = ElyraAmoled_Error,
            isAmoled = true
        )
        darkTheme -> ElyraExtendedColors(
            surface = ElyraDark_Surface,
            surfaceVariant = ElyraDark_Surfacevariant,
            onSurface = ElyraDark_Onsurface,
            onSurfaceVariant = ElyraDark_Onsurfacevariant,
            primary = ElyraDark_Primary,
            onPrimary = ElyraDark_Onprimary,
            secondary = ElyraDark_Secondary,
            outline = ElyraDark_Outline,
            success = ElyraDark_Success,
            warning = ElyraDark_Warning,
            error = ElyraDark_Error,
            isAmoled = false
        )
        else -> ElyraExtendedColors(
            surface = ElyraLight_Surface,
            surfaceVariant = ElyraLight_Surfacevariant,
            onSurface = ElyraLight_Onsurface,
            onSurfaceVariant = ElyraLight_Onsurfacevariant,
            primary = ElyraLight_Primary,
            onPrimary = ElyraLight_Onprimary,
            secondary = ElyraLight_Secondary,
            outline = ElyraLight_Outline,
            success = ElyraLight_Success,
            warning = ElyraLight_Warning,
            error = ElyraLight_Error,
            isAmoled = false
        )
    }

    val materialColors: ColorScheme = if (darkTheme || amoled) {
        darkColorScheme(
            primary = extendedColors.primary,
            onPrimary = extendedColors.onPrimary,
            surface = extendedColors.surface,
            onSurface = extendedColors.onSurface,
            surfaceVariant = extendedColors.surfaceVariant,
            outline = extendedColors.outline,
            error = extendedColors.error
        )
    } else {
        lightColorScheme(
            primary = extendedColors.primary,
            onPrimary = extendedColors.onPrimary,
            surface = extendedColors.surface,
            onSurface = extendedColors.onSurface,
            surfaceVariant = extendedColors.surfaceVariant,
            outline = extendedColors.outline,
            error = extendedColors.error
        )
    }

    CompositionLocalProvider(LocalElyraColors provides extendedColors) {
        MaterialTheme(
            colorScheme = materialColors,
            shapes = ElyraShapes,
            content = content
        )
    }
}

object ElyraTheme {
    val colors: ElyraExtendedColors
        @Composable
        get() = LocalElyraColors.current
}
''')
    print("Successfully generated Elyra design tokens in XML and Kotlin Compose!")

if __name__ == "__main__":
    main()
