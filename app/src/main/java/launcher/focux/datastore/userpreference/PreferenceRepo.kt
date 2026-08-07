package launcher.focux.datastore.userpreference

import android.content.Context
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import launcher.focux.utils.BottomWidgetEnum
import launcher.focux.utils.ThemeEnum
import launcher.focux.utils.TopWidgetEnum

class PreferenceRepo(
    private val ctx: Context
) {

    val setting : Flow<PreferenceModel> = ctx.preferenceDatastore.data
        .catch { exception ->
            throw exception
        }

    suspend fun changeTheme(theme: ThemeEnum) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(theme = theme)
        }
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

    suspend fun changeTopWidget(widget: TopWidgetEnum) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(topWidgetEnum = widget)
        }
    }

    suspend fun changeBottomWidget(widget: BottomWidgetEnum) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(bottomWidget = widget)
        }
    }

    suspend fun toggleWallpaper(visibility: Boolean) {
        ctx.preferenceDatastore.updateData { current ->
            current.copy(showWallpaper = visibility)
        }
    }

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