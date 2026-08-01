package launcher.focux.ui.component

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import launcher.focux.R
import launcher.focux.utils.AppModel
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import launcher.focux.datastore.app.ApplicationRepo
import launcher.focux.datastore.pinnedapp.PinnedApp
import launcher.focux.datastore.pinnedapp.PinnedAppRepo
import launcher.focux.ui.component.popup.RenamePopup
import launcher.focux.viewmodel.DrawerViewmodel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(sheetState: BottomSheetScaffoldState, viewmodel: DrawerViewmodel) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val appModel = viewmodel.selectedApp.collectAsStateWithLifecycle().value

    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 16.dp)
        ) {
            Button(
                onClick =  {

                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                    intent.setData("package:${appModel.packageName}".toUri())
                    context.startActivity(intent)
                    coroutineScope.launch {
                        sheetState.bottomSheetState.partialExpand()

                    }
                },
                modifier = Modifier
                    .height(64.dp)
                    .weight(0.5f)
                    .padding(end = 6.dp),
                shape = RoundedCornerShape(
                    16.dp
                )
            ) {
                Icon(
                    painter = painterResource(R.drawable.lucide_info),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(16.dp)
                )
                Text(
                    text = "App Info"
                )
            }
            Button(
                onClick =  {
                    Toast.makeText(context, "clicked", Toast.LENGTH_SHORT).show()
                    viewmodel.toggleTimer()
                    coroutineScope.launch {
                        sheetState.bottomSheetState.partialExpand()
                    }
                },
                shape = RoundedCornerShape(
                    16.dp
                ),
                modifier = Modifier
                    .height(64.dp)
                    .weight(0.5f)
                    .padding(start = 6.dp),
            ) {
                Icon(
                    painter = painterResource(R.drawable.lucide_ban),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .size(16.dp)
                )
                Text(
                    text = "Lock"
                )
            }
        }

        Button(
            onClick =  {
                coroutineScope.launch(Dispatchers.IO) {
                    ApplicationRepo(context).hide(AppModel(appModel.name, appModel.packageName, true))
                    sheetState.bottomSheetState.partialExpand()
                }
            },
            shape = RoundedCornerShape(
                16.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .height(54.dp)

        ) {
            Icon(
                painter = painterResource(R.drawable.lucide_circle_off),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
            )
            Text(
                text = "Hide App"
            )
        }

        Button(
            onClick =  {
                coroutineScope.launch(Dispatchers.IO) {
                    PinnedAppRepo(context).add(
                        PinnedApp(
                            appModel.name,
                            appModel.packageName
                        )
                    )
                    sheetState.bottomSheetState.partialExpand()
                }
            },
            shape = RoundedCornerShape(
                16.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .height(54.dp)

        ) {
            Icon(
                painter = painterResource(R.drawable.lucide_bookmark),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
            )
            Text(
                text = "Pin App"
            )
        }

        Button(
            onClick =  {
                viewmodel.toggleShow()
                coroutineScope.launch {
                    sheetState.bottomSheetState.partialExpand()
                }
            },
            shape = RoundedCornerShape(
                16.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 12.dp)
                .height(54.dp)

        ) {
            Icon(
                painter = painterResource(R.drawable.lucide_pencil),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
            )
            Text(
                text = "Rename App"
            )
        }

        Button(
            onClick =  {
                val intent = Intent(Intent.ACTION_DELETE)
                intent.setData("package:${appModel.packageName}".toUri())
                context.startActivity(intent)
                coroutineScope.launch {
                    sheetState.bottomSheetState.partialExpand()
                }
            },
            shape = RoundedCornerShape(
                16.dp
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)
                .height(54.dp)

        ) {
            Icon(
                painter = painterResource(R.drawable.lucide_trash),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 10.dp)
                    .size(16.dp)
            )
            Text(
                text = "Delete App"
            )
        }
    }
}