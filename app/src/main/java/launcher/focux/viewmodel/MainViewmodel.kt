package launcher.focux.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.application
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import launcher.focux.datastore.lockedapp.LockedApp
import launcher.focux.datastore.lockedapp.LockedAppModel
import launcher.focux.datastore.lockedapp.LockedAppRepo
import launcher.focux.datastore.lockedapp.lockedAppDatastore
import launcher.focux.utils.AppModel
import launcher.focux.datastore.pinnedapp.PinnedApp
import launcher.focux.datastore.pinnedapp.PinnedAppRepo
import launcher.focux.datastore.userpreference.PreferenceRepo
import launcher.focux.datastore.userpreference.PreferenceModel
import launcher.focux.datastore.userpreference.preferenceDatastore
import launcher.focux.utils.Packages
import launcher.focux.utils.sort
import java.util.Collections.emptySortedMap

class MainViewmodel(application: Application) : AndroidViewModel(application) {

    val _pinnedAppRepo = PinnedAppRepo(application)

    val pinnedApp: StateFlow<List<PinnedApp>> = _pinnedAppRepo.pinnedAppFlow.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList<PinnedApp>()
    )

    val setting: StateFlow<PreferenceModel?> = application.preferenceDatastore.data.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = PreferenceModel()
    )

    val lockedApp: StateFlow<List<LockedApp>> = LockedAppRepo(application).lockedApps.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList<LockedApp>()
    )
}