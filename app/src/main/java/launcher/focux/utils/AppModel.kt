package launcher.focux.utils

import kotlinx.serialization.Serializable

@Serializable
data class AppModel(
    val name: String = "",
    val packageName: String = "",
    val isHidden: Boolean = false
)