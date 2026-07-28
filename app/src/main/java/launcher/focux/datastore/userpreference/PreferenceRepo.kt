package launcher.focux.datastore.userpreference

import android.content.Context
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import launcher.focux.utils.BottomWidget
import launcher.focux.utils.TopWidget

class PreferenceRepo(
    private val ctx: Context
) {

    val setting : Flow<PreferenceModel> = ctx.preferenceDatastore.data
        .catch { exception ->
            throw exception
        }

    suspend fun toggleStatusBar(visibility: Boolean) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(showStatusBar = visibility)
        }
    }

    suspend fun toggleClock(visibility: Boolean) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(showClock = visibility)
        }
    }

    /***
     * true = 12 hour format
     * false = 24 hour format
     ***/
    suspend fun changeClockFormat(format: Boolean) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(clockFormat = format)
        }
    }

    suspend fun changeFont(font: Int) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(font = font)
        }
    }

    suspend fun changeTopWidget(widget: TopWidget) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(topWidget = widget)
        }
    }

    suspend fun changeBottomWidget(widget: BottomWidget) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(bottomWidget = widget)
        }
    }

    // PreferenceRepo.kt
    suspend fun checkAndSetFirstLaunch(): Boolean {
        var wasFirstTime = false
        ctx.preferenceDatastore.updateData { current ->
            if (current.isFreshInstall) {
                wasFirstTime = true
                current.copy(isFreshInstall = false)
            } else {
                current
            }
        }
        return wasFirstTime
    }
}