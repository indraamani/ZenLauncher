package launcher.focux.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import launcher.focux.ui.screen.SearchScreen
import launcher.focux.ui.theme.FocuxTheme
import launcher.focux.viewmodel.DrawerViewmodel

class SearchActivity : ComponentActivity() {
    private val viewModel : DrawerViewmodel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FocuxTheme {
                SearchScreen(viewModel)
            }
        }
    }
}