package launcher.focux.datastore.userpreference

import kotlinx.serialization.Serializable
import launcher.focux.R
import launcher.focux.utils.BottomWidgetEnum
import launcher.focux.utils.TopWidgetEnum

@Serializable
data class PreferenceModel(
    var showStatusBar: Boolean = true,
    val showClock: Boolean = true,
    val clockFormat: Boolean = false, /* false = 24 hours format, true = 12 hours format */
    val font: Int = R.font.valorant,
    val isFreshInstall: Boolean = true,
    val topWidget: TopWidgetEnum = TopWidgetEnum.DEFAULT,
    val bottomWidget: BottomWidgetEnum = BottomWidgetEnum.DEFAULT,
)