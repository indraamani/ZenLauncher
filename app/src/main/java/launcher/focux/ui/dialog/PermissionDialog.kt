package launcher.focux.ui.dialog

import android.content.Intent
import android.provider.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.core.net.toUri

@Composable
fun PermissionDialog(
    show: Boolean,
    title: String,
    text: String,
    settingActivity: String,
) {
    val context = LocalContext.current

    if (show) {
        AlertDialog(
            onDismissRequest = {},
            title = {
                Text(
                    text = title
                )
            },
            text = {
                Text(
                    text = text
                )
            },
            confirmButton = {
                OutlinedButton(
                    onClick = {
                        val intent = Intent(settingActivity)
                        if(settingActivity != Settings.ACTION_APP_NOTIFICATION_SETTINGS) intent.data = "package:${context.packageName}".toUri() else intent.putExtra(
                            Settings.EXTRA_APP_PACKAGE, context.packageName)
                        context.startActivity(intent)
                    }
                ) {
                    Text(
                        text = "Grant"
                    )
                }
            },
            dismissButton = null
        )
    }
}