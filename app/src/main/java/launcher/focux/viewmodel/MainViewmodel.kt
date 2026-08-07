package launcher.focux.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import launcher.focux.datastore.lockedapp.LockedApp
import launcher.focux.datastore.lockedapp.LockedAppRepo
import launcher.focux.datastore.pinnedapp.PinnedApp
import launcher.focux.datastore.pinnedapp.PinnedAppRepo
import launcher.focux.datastore.userpreference.PreferenceModel
import launcher.focux.datastore.userpreference.preferenceDatastore

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