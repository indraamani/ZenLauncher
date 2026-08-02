package launcher.focux.datastore.lockedapp

import kotlinx.serialization.Serializable
import java.time.LocalTime

@Serializable
data class LockedApp(
    val name : String = "",
    val packageName: String = "",
    val initialTime: String = "",
    val lockedTime: Long = 0
)

@Serializable
data class LockedAppModel(
    val appList: List<LockedApp> = emptyList()
)