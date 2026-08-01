package launcher.focux.datastore.lockedapp

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.LocalTime


class LockedAppRepo(
    private val context: Context
) {
    val lockedApps : Flow<List<LockedApp>> = context.lockedAppDatastore.data
        .map { app -> app.appList }

    private fun hasTimePassed(initialTime: String, min: Long) : Boolean {
        val initialTime = LocalTime.parse(initialTime)
        initialTime.plusMinutes(min)
        val currentTime = LocalTime.now()

        return currentTime.isAfter(initialTime)
    }

    suspend fun add(lockedApp: LockedApp) {
        context.lockedAppDatastore.updateData { current ->
            val list = current.appList.filterNot {
                it.packageName == lockedApp.packageName
            }
            current.copy(appList = (list + lockedApp))
        }

    }

    suspend fun remove(packageName: String) {
        context.lockedAppDatastore.updateData { current ->
            val list = current.appList.toMutableList().filter {
                hasTimePassed(it.initialTime, it.lockedTime)
            }
            current.copy(list)
        }
    }

    suspend fun hasTimePassed(packageName: String) : Boolean{
        val list = context.lockedAppDatastore.data.first().appList
        return list.any {
            it.packageName == packageName && hasTimePassed(it.initialTime, it.lockedTime)
        }
    }
}