package launcher.focux.utils

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.os.SystemClock
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.TIRAMISU)
fun getRecentUsedApps(ctx: Context): List<AppModel> {
    val usageStateManger = ctx.getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
    val appList = mutableListOf<AppModel>()
    val currentTime : Long = System.currentTimeMillis()
    val lastHour : Long = currentTime - (1000 * 60 * 60 * 2)

    val usageStateList : List<UsageStats> = usageStateManger.queryUsageStats(
        UsageStatsManager.INTERVAL_DAILY,
        lastHour,
        currentTime
    )

    if (usageStateList.isNotEmpty()) {
        usageStateList.sortedBy {
            it.lastTimeUsed
        }.reversed().forEach {
            val name = ctx.packageManager.getApplicationLabel(ctx.packageManager.getApplicationInfo(it.packageName,
                PackageManager.ApplicationInfoFlags.of(0)))
            val launcherActivityInfo = ctx.packageManager.getLaunchIntentForPackage(it.packageName)
            if(launcherActivityInfo != null) {
                appList.add(
                    AppModel(
                        name as String,
                        it.packageName
                    )
                )
            }
        }
    }

    return appList.slice(0..4)
}