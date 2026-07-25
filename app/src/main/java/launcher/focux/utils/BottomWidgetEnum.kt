package launcher.focux.utils

import kotlinx.serialization.Serializable

@Serializable
enum class BottomWidgetEnum {
    DEFAULT,

    DAYWIDGET,
    HOURGRID,
    MONTHGRID,
    YEARGRID
}