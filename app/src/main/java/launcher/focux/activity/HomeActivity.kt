package launcher.focux.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import launcher.focux.MainActivity
import launcher.focux.ui.dialog.PermissionDialog
import launcher.focux.ui.theme.FocuxTheme

class HomeActivity : ComponentActivity() {
    private val allPermission = arrayOf(
        arrayOf(
            Manifest.permission.PACKAGE_USAGE_STATS,
            "Permit usage access",
            "Usage access allows an app to track what other apps you're using or recently used.",
            Settings.ACTION_USAGE_ACCESS_SETTINGS
        ),
        arrayOf(
            Manifest.permission.SYSTEM_ALERT_WINDOW,
            "Enable Floating Window",
            "This app needs permission to display over other apps.",
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION
        ),
        arrayOf(
            Manifest.permission.POST_NOTIFICATIONS,
            "Enable Notifications",
            "Keep notifications turned on to allow background features to work properly.",
            Settings.ACTION_APP_NOTIFICATION_SETTINGS
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            FocuxTheme {
                var checkTrigger by remember { mutableStateOf(0) }
                DisposableEffect(Unit) {
                    val observer = LifecycleEventObserver { _, event ->
                        if (event == Lifecycle.Event.ON_RESUME) {
                            checkTrigger++
                        }
                    }
                    lifecycle.addObserver(observer)
                    onDispose { lifecycle.removeObserver(observer) }
                }

                val missingPermission = remember(checkTrigger) {
                    allPermission.firstOrNull { !havePermission(it[0]) }
                }

                if (missingPermission != null) {
                    PermissionDialog(
                        show = true,
                        title = missingPermission[1],
                        text = missingPermission[2],
                        settingActivity = missingPermission[3]
                    )
                } else {
                    LaunchedEffect(Unit) {
                        startActivity(Intent(this@HomeActivity, MainActivity::class.java))
                        finish()
                    }
                }
            }
        }
    }

    @SuppressLint("ServiceCast")
    fun havePermission(permissionName: String): Boolean {
        return when (permissionName) {
            Manifest.permission.PACKAGE_USAGE_STATS -> {
                val usageStatsManager = getSystemService(Context.USAGE_STATS_SERVICE) as UsageStatsManager
                val currentTime = System.currentTimeMillis()
                val stats = usageStatsManager.queryUsageStats(
                    UsageStatsManager.INTERVAL_DAILY,
                    currentTime - 10000,
                    currentTime
                )

                !stats.isNullOrEmpty()
            }
            Manifest.permission.SYSTEM_ALERT_WINDOW -> Settings.canDrawOverlays(this)
            else -> ContextCompat.checkSelfPermission(this, permissionName) == PackageManager.PERMISSION_GRANTED
        }
    }
}