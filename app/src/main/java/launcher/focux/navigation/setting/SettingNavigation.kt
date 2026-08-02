package launcher.focux.navigation.setting

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import launcher.focux.ui.screen.BottomWidgetScreen
import launcher.focux.ui.screen.FontScreen
import launcher.focux.ui.screen.HiddenAppScreen
import launcher.focux.ui.screen.LockedAppScreen
import launcher.focux.ui.screen.PinnedAppScreen
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
                openBottomwidgetScreen = {
                    navController.navigate(BottomWidgetScreen)
                },
                openPinnedAppScreen = {
                    navController.navigate(PinnedAppScreen)
                },
                openHiddenAppScreen = {
                    navController.navigate(HiddenAppScreen)
                },
                openLockedAppScreen = {
                    navController.navigate(LockedAppScreen)
                }
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

        composable<BottomWidgetScreen> {
            BottomWidgetScreen(
                viewmodel = viewmodel,
                closeScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable<PinnedAppScreen> {
            PinnedAppScreen(
                viewmodel = viewmodel,
                closeScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable<HiddenAppScreen> {
            HiddenAppScreen(
                viewmodel = viewmodel,
                closeScreen = {
                    navController.popBackStack()
                }
            )
        }

        composable<LockedAppScreen> {
            LockedAppScreen(
                viewmodel = viewmodel,
                closeScreen = {
                    navController.popBackStack()
                }
            )
        }

//        composable<ThemeScreen> {
//        }
//        composable<GestureScreen> {
//        }
    }

}