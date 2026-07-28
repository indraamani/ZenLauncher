package launcher.focux.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import launcher.focux.R
import launcher.focux.datastore.userpreference.PreferenceRepo
import launcher.focux.ui.component.widget.BoxedClock
import launcher.focux.ui.component.widget.Clock
import launcher.focux.ui.component.widget.DateClockWidget
import launcher.focux.ui.component.widget.DateWidget
import launcher.focux.ui.component.widget.DayClockWidget
import launcher.focux.ui.component.widget.DayWidget
import launcher.focux.ui.component.widget.HourGrid
import launcher.focux.ui.component.widget.MonthGrid
import launcher.focux.ui.component.widget.YearGrid
import launcher.focux.utils.TopWidget
import launcher.focux.viewmodel.SettingViewmodel

@Composable
fun Container(
    onclick: () -> Unit,
    content: @Composable () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .padding(vertical = 20.dp)
            .clip(
                RoundedCornerShape(120.dp)
            )
            .background(Color.White.copy(alpha = 0.1f))
            .combinedClickable(
                enabled = true,
                indication = null,
                interactionSource = null,
                onClick = {
                     onclick()
                }
            )
    ) {
        content()
    }
}

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
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.BOXED_CLOCK)
                        }
                    }) {
                        BoxedClock(setting.font, setting.clockFormat)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.CLOCK)
                        }
                    }) {
                        Clock(setting.font, setting.clockFormat)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.DAY)
                        }
                    }) {
                        DayWidget(setting.font)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.DATE)
                        }
                    }) {
                        DateWidget(setting.font)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.DAYCLOCK)
                        }
                    }) {
                        DayClockWidget(setting.font, setting.clockFormat)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.DATECLOCK)
                        }
                    }) {
                        DateClockWidget(setting.font, setting.clockFormat)
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.HOURGRID)
                        }
                    }) {
                        HourGrid()
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.MONTHGRID)
                        }
                    }) {
                        MonthGrid()
                    }
                }

                item {
                    Container(onclick = {
                        coroutineScope.launch(Dispatchers.IO) {
                            PreferenceRepo(ctx).changeTopWidget(TopWidget.YEARGRID)
                        }
                    }) {
                        YearGrid()
                    }
                }
            }

        }
    }
}