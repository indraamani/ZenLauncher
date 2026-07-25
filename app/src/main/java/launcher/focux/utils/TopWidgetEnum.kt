package launcher.focux.utils

import kotlinx.serialization.Serializable

@Serializable
enum class TopWidgetEnum {
    DEFAULT,
    BOXED_CLOCK,
    CLOCK,
    DAY,
    DATE,
    DAYCLOCK,
    DATECLOCK,
    HOURGRID,
    MONTHGRID,
    YEARGRID

}