package launcher.focux.navigation.setting

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import launcher.focux.ui.screen.FontScreen
import launcher.focux.ui.screen.SettingScreen
import launcher.focux.ui.screen.TopWidgetScreen
import launcher.focux.viewmodel.SettingViewmodel

@Composable
fun SettingNavigation(viewmodel: SettingViewmodel){

    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Setting) {

        composable<Setting> {
            SettingScreen(
                viewmodel = viewmodel,
                openFontScreen = {
                    navController.navigate(FontScreen)
                },
                openTopwidgetScreen = {
                    navController.navigate(TopWidgetScreen)
                },
            )
        }
        composable<FontScreen> {

            FontScreen(
                viewmodel = viewmodel,
                closeScreen = {
                    navController.popBackStack()

                }
            )
        }
        composable<TopWidgetScreen> {
            TopWidgetScreen(
                viewmodel = viewmodel,
                closeScreen = {
                    navController.popBackStack()
                }
            )
        }

//        composable<ThemeScreen> {
//        }
//        composable<BottomWidgetScreen> {
//        }
//        composable<GestureScreen> {
//        }
    }

}