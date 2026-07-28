package launcher.focux.datastore.userpreference

import kotlinx.serialization.Serializable
import launcher.focux.R
import launcher.focux.utils.BottomWidget
import launcher.focux.utils.TopWidget

@Serializable
data class PreferenceModel(
    var showStatusBar: Boolean = true,
    val showClock: Boolean = true,
    val clockFormat: Boolean = false, /* false = 24 hours format, true = 12 hours format */
    val font: Int = R.font.valorant,
    val isFreshInstall: Boolean = true,
    val topWidget: TopWidget = TopWidget.DEFAULT,
    val bottomWidget: BottomWidget = BottomWidget.DEFAULT,
)