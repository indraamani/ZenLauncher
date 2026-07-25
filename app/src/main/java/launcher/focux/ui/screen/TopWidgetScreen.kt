package launcher.focux.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import launcher.focux.R
import launcher.focux.datastore.userpreference.PreferenceRepo
import launcher.focux.ui.component.Container
import launcher.focux.ui.component.widget.BoxedClock
import launcher.focux.ui.component.widget.Clock
import launcher.focux.ui.component.widget.DateClockWidget
import launcher.focux.ui.component.widget.DateWidget
import launcher.focux.ui.component.widget.DayClockWidget
import launcher.focux.ui.component.widget.DayWidget
import launcher.focux.ui.component.widget.HourGrid
import launcher.focux.ui.component.widget.MonthGrid
import launcher.focux.ui.component.widget.YearGrid
import launcher.focux.utils.TopWidgetEnum
import launcher.focux.viewmodel.SettingViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopWidgetScreen(
    viewmodel: SettingViewmodel,
    closeScreen: () -> Unit
) {
    val ctx = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val setting = viewmodel.setting.collectAsStateWithLifecycle().value

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(
                        onClick = {
                            closeScreen()
                        }
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.arrow),
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Text(
                text = "Choose Top Widget",
                fontFamily = FontFamily(
                    Font(setting.font)
                ),
                modifier = Modifier
                    .padding(horizontal = 12.dp, 30.dp),
                fontSize = 22.sp,
            )

            LazyColumn(
                contentPadding = PaddingValues(horizontal = 20.dp)
            ) {
                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.DEFAULT)
                        }
                    }) {
                        Text(
                            text = "No Widget",
                            fontSize = 16.sp
                        )
                    }
                }
                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.BOXED_CLOCK)
                        }
                    }) {
                        BoxedClock(setting.font, setting.clockFormat)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.CLOCK)
                        }
                    }) {
                        Clock(setting.font, setting.clockFormat)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.DAY)
                        }
                    }) {
                        DayWidget(setting.font)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.DATE)
                        }
                    }) {
                        DateWidget(setting.font)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.DAYCLOCK)
                        }
                    }) {
                        DayClockWidget(setting.font, setting.clockFormat)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.DATECLOCK)
                        }
                    }) {
                        DateClockWidget(setting.font, setting.clockFormat)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.HOURGRID)
                        }
                    }) {
                        HourGrid()
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.MONTHGRID)
                        }
                    }) {
                        MonthGrid()
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidgetEnum.YEARGRID)
                        }
                    }) {
                        YearGrid()
                    }
                }
            }

        }
    }
}