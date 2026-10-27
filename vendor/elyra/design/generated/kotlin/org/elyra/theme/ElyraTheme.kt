package org.elyra.theme

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
