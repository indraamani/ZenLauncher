package launcher.focux.datastore.userpreference

import kotlinx.serialization.Serializable
import launcher.focux.R
import launcher.focux.utils.BottomWidgetEnum
import launcher.focux.utils.ThemeEnum
import launcher.focux.utils.TopWidgetEnum

@Serializable
data class PreferenceModel(
    var showStatusBar: Boolean = true,
    val showClock: Boolean = true,
    val clockFormat: Boolean = false, /* false = 24 hours format, true = 12 hours format */
    val showWallpaper: Boolean = true,
    val font: Int = R.font.comfortaa_bold,
    val isFreshInstall: Boolean = true,
    val theme: ThemeEnum = ThemeEnum.SunsetAmber,
    val topWidgetEnum: TopWidgetEnum = TopWidgetEnum.DEFAULT,
    val bottomWidget: BottomWidgetEnum = BottomWidgetEnum.DEFAULT,
)