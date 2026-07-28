package launcher.focux.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import launcher.focux.navigation.setting.SettingNavigation
import launcher.focux.ui.theme.FocuxTheme
import launcher.focux.viewmodel.SettingViewmodel

class SettingActivity : ComponentActivity() {

    private val viewModel : SettingViewmodel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocuxTheme {
                SettingNavigation(
                    viewModel
                )
            }
        }
    }
}