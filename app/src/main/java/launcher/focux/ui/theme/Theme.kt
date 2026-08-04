package launcher.focux.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import launcher.focux.datastore.userpreference.PreferenceModel
import launcher.focux.datastore.userpreference.preferenceDatastore
import launcher.focux.utils.ThemeEnum


val CyberNeonColorScheme = darkColorScheme(
    primary = Color(0xFF00E5FF),
    onPrimary = Color(0xFF00363A),
    surface = Color(0xFF121418),
    surfaceContainer = Color(0xFF1E222A),
    onSurface = Color(0xFFF0F4F8),
    onSurfaceVariant = Color(0xFF9EACBA)
)

val CyberNeonLightColorScheme = lightColorScheme(
    primary = Color(0xFF006875),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFF6F9FB),
    surfaceContainer = Color(0xFFE3EDF2),
    onSurface = Color(0xFF0F1A1C),
    onSurfaceVariant = Color(0xFF536267)
)

val EmeraldMidnightColorScheme = darkColorScheme(
    primary = Color(0xFF00E676),
    onPrimary = Color(0xFF003816),
    surface = Color(0xFF0D1117),
    surfaceContainer = Color(0xFF161B22),
    onSurface = Color(0xFFE6EDF3),
    onSurfaceVariant = Color(0xFF8B949E)
)

val EmeraldLightColorScheme = lightColorScheme(
    primary = Color(0xFF006D38),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFF7FBF7),
    surfaceContainer = Color(0xFFE4EFE5),
    onSurface = Color(0xFF0E1F15),
    onSurfaceVariant = Color(0xFF516355)
)

val SunsetAmberColorScheme = darkColorScheme(
    primary = Color(0xFFFFB300),
    onPrimary = Color(0xFF3E2700),
    surface = Color(0xFF181210),
    surfaceContainer = Color(0xFF261E1A),
    onSurface = Color(0xFFFBEFEA),
    onSurfaceVariant = Color(0xFFB5A49B)
)

val SunsetAmberLightColorScheme = lightColorScheme(
    primary = Color(0xFF8B5000),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFFFF8F5),
    surfaceContainer = Color(0xFFFAF0E6),
    onSurface = Color(0xFF231A12),
    onSurfaceVariant = Color(0xFF6B5D53)
)

val DeepPurpleColorScheme = darkColorScheme(
    primary = Color(0xFFCFBCFF),
    onPrimary = Color(0xFF381E72),
    surface = Color(0xFF141218),
    surfaceContainer = Color(0xFF211F26),
    onSurface = Color(0xFFE6E0E9),
    onSurfaceVariant = Color(0xFFCAC4D0)
)

val DeepPurpleLightColorScheme = lightColorScheme(
    primary = Color(0xFF6750A4),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFFEF7FF),
    surfaceContainer = Color(0xFFF3EDF7),
    onSurface = Color(0xFF1D1B20),
    onSurfaceVariant = Color(0xFF49454F)
)

val ElectricCoralColorScheme = darkColorScheme(
    primary = Color(0xFFFF6B6B),
    onPrimary = Color(0xFF4A0007),
    surface = Color(0xFF0F141C),
    surfaceContainer = Color(0xFF1B222D),
    onSurface = Color(0xFFEDF2F7),
    onSurfaceVariant = Color(0xFFA0AEC0)
)

val ElectricCoralLightColorScheme = lightColorScheme(
    primary = Color(0xFFB91C32),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFFAFAFC),
    surfaceContainer = Color(0xFFEEF0F5),
    onSurface = Color(0xFF191C21),
    onSurfaceVariant = Color(0xFF5D626E)
)

val BotanicalColorScheme = darkColorScheme(
    primary = Color(0xFFF48FB1),
    onPrimary = Color(0xFF4A0021),
    surface = Color(0xFF0B1512),
    surfaceContainer = Color(0xFF162420),
    onSurface = Color(0xFFE8F5E9),
    onSurfaceVariant = Color(0xFFA3B899)
)

val BotanicalLightColorScheme = lightColorScheme(
    primary = Color(0xFF9C2A58),
    onPrimary = Color(0xFFFFFFFF),
    surface = Color(0xFFFCF8F9),
    surfaceContainer = Color(0xFFF5E8ED),
    onSurface = Color(0xFF23191D),
    onSurfaceVariant = Color(0xFF67545B)
)

@Composable
fun FocuxTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    val theme : ThemeEnum = context.preferenceDatastore.data.stateIn(
        coroutineScope,
        SharingStarted.Eagerly,
        initialValue = PreferenceModel()
    ).collectAsStateWithLifecycle().value.theme

    val colorScheme = if (darkTheme) {
        when (theme) {
            ThemeEnum.CyberNeon -> CyberNeonColorScheme
            ThemeEnum.EmeraldMidnight -> EmeraldMidnightColorScheme
            ThemeEnum.SunsetAmber -> SunsetAmberColorScheme
            ThemeEnum.DeepPurple -> DeepPurpleColorScheme
            ThemeEnum.ElectricCoral -> ElectricCoralColorScheme
            ThemeEnum.Botanical -> BotanicalColorScheme
        }
    } else {
        when (theme) {
            ThemeEnum.CyberNeon -> CyberNeonLightColorScheme
            ThemeEnum.EmeraldMidnight -> EmeraldLightColorScheme
            ThemeEnum.SunsetAmber -> SunsetAmberLightColorScheme
            ThemeEnum.DeepPurple -> DeepPurpleLightColorScheme
            ThemeEnum.ElectricCoral -> ElectricCoralLightColorScheme
            ThemeEnum.Botanical -> BotanicalLightColorScheme
        }
    }

    MaterialTheme(colorScheme = colorScheme, content = content)
}