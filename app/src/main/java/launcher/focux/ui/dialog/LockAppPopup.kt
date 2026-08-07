package launcher.focux.ui.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import launcher.focux.datastore.lockedapp.LockedApp
import launcher.focux.datastore.lockedapp.LockedAppRepo
import launcher.focux.viewmodel.DrawerViewmodel
import java.time.LocalDateTime

@Composable
fun LockAppPopup(viewmodel: DrawerViewmodel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val selectedApp = viewmodel.selectedApp.collectAsState().value
    var slider by remember { mutableIntStateOf(1) }

    if (viewmodel.showTimer.collectAsState().value) {
        Dialog(
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            ),
            onDismissRequest = {
                viewmodel.toggleShow()
            }
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(200.dp),
                colors = CardDefaults.cardColors()
            ) {
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp)
                ) {
                    Text(
                        text = "Set Timer",
                        fontSize = 20.sp
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier
                            .padding(vertical = 20.dp)
                    ) {
                        Slider(
                            value = slider.toFloat(),
                            valueRange = 1f..24f,
                            steps = 23,
                            onValueChange = {
                                slider = it.toInt()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(0.8f)
                                .padding(end = 10.dp)

                        )
                        Text(
                            text = slider.toString(),
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 16.sp
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewmodel.toggleTimer()
                            },
                            shape = RoundedCornerShape(8.dp)
                        ) {
                            Text(
                                text = "Cancel"
                            )
                        }
                        Button(
                            onClick = {
                                coroutineScope.launch(Dispatchers.IO) {
                                    LockedAppRepo(context).add(
                                        LockedApp(
                                            selectedApp.name,
                                            selectedApp.packageName,
                                            LocalDateTime.now().toString(),
                                            slider.toLong()*60
                                        )
                                    )
                                }
                                viewmodel.toggleTimer()
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = "Add"
                            )
                        }
                    }
                }
            }
        }
    }

}