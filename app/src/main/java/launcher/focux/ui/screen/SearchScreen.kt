package launcher.focux.ui.screen

import android.app.Activity
import android.app.SearchManager
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.staggeredgrid.LazyHorizontalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import launcher.focux.R
import launcher.focux.datastore.lockedapp.LockedAppRepo
import launcher.focux.ui.component.BottomSheet
import launcher.focux.ui.component.Folder
import launcher.focux.viewmodel.DrawerViewmodel

@Composable
fun SearchScreen(viewmodel: DrawerViewmodel) {
    val text : String = viewmodel.searchText.collectAsStateWithLifecycle().value
    val itemsList = viewmodel.recentApps
    val context = LocalContext.current
    val lockedApp = viewmodel.lockedApp.collectAsStateWithLifecycle().value
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                OutlinedTextField(
                    value = viewmodel.searchText.collectAsStateWithLifecycle().value,
                    onValueChange = {
                        viewmodel.updateSearchText(it)
                    },
                    placeholder = {
                        Text(
                            text = "Search",
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(top = 54.dp, bottom = 27.dp)
                        .onFocusChanged(
                            onFocusChanged = {
                                viewmodel.toggleTextFieldState(it.hasFocus)
                            }
                        ),
                    shape = RoundedCornerShape(
                        54.dp
                    )
                )
            }
        },

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
        ) {
            if (text.isEmpty()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 16.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.lucide_sparkle),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Recent Apps",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                LazyVerticalStaggeredGrid(
                    columns = StaggeredGridCells.Fixed(3),
                    modifier = Modifier.fillMaxWidth(),
                    contentPadding = PaddingValues(16.dp),
                    verticalItemSpacing = 8.dp,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(itemsList) {
                        OutlinedButton(
                            onClick = {
                                coroutineScope.launch {
                                    val app =
                                        lockedApp.filter { app -> it.packageName == app.packageName }

                                    if (app.isNotEmpty()) {
                                        val isLocked = withContext(Dispatchers.IO) {
                                            LockedAppRepo(context).hasTimePassed(it.packageName)
                                        }
                                        if (!isLocked) {
                                            Toast.makeText(
                                                context,
                                                "App Locked",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                            return@launch
                                        } else {
                                            withContext(Dispatchers.IO) {
                                                LockedAppRepo(context).remove(it.packageName)
                                            }
                                            context.startActivity(
                                                context.packageManager.getLaunchIntentForPackage(
                                                    it.packageName
                                                )
                                            )
                                            (context as Activity).finish()
                                        }
                                    } else {
                                        context.startActivity(
                                            context.packageManager.getLaunchIntentForPackage(
                                                it.packageName
                                            )
                                        )
                                        (context as Activity).finish()
                                    }
                                }
                            },
                            modifier = Modifier
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(6.dp),
                            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
                        ) {
                            Text(
                                text = it.name,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            } else {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.lucide_sparkle),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Search On",
                        fontSize = 18.sp,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(start = 16.dp, bottom = 16.dp)
                ) {
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_WEB_SEARCH).apply {
                                putExtra(SearchManager.QUERY, text)
                            }
                            context.startActivity(intent)
                        },
                    ) {
                        Text(
                            text = "Web",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                    Button(
                        onClick = {
                            val intent = Intent(Intent.ACTION_SEARCH).apply {
                                setType(ContactsContract.Contacts.CONTENT_TYPE)
                                    .putExtra(SearchManager.QUERY, text)
                            }
                            context.startActivity(intent)
                        },
                    ) {
                        Text(
                            text = "Contact",
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
                Folder(
                    viewmodel
                )
            }
        }
    }
}