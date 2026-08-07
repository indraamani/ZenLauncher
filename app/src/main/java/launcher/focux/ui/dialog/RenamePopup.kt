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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import launcher.focux.datastore.app.ApplicationRepo
import launcher.focux.utils.AppModel
import launcher.focux.utils.Packages
import launcher.focux.viewmodel.DrawerViewmodel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RenamePopup(viewmodel: DrawerViewmodel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val selectedApp = viewmodel.selectedApp.collectAsState().value
    var name by remember { mutableStateOf(selectedApp.name) }

    if (viewmodel.show.collectAsState().value) {
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
                        text = "Rename App",
                        fontSize = 20.sp
                    )

                    OutlinedTextField(
                        value = name,
                        onValueChange = { name  = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 20.dp),
                        placeholder = {
                            Text(
                                text = "App name"
                            )
                        },

                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        OutlinedButton(
                            onClick = {
                                viewmodel.toggleShow()
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
                                    val pkg = Packages(context).fetchAllPackages().toMutableList()
                                    pkg.apply {
                                        removeIf {
                                            it.packageName == selectedApp.packageName
                                        }
                                        add(
                                            AppModel(
                                                name,
                                                selectedApp.packageName
                                            )
                                        )
                                    }
                                    ApplicationRepo(context) .rename(selectedApp.name, AppModel(name, selectedApp.packageName))
//                                        .update(
//                                            pkg.sort()
//                                        )
                                }
                                viewmodel.toggleShow()
                            },
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.padding(start = 12.dp)
                        ) {
                            Text(
                                text = "Change"
                            )
                        }
                    }
                }
            }
        }
    }
}