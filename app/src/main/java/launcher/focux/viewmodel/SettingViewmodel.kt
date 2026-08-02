package launcher.focux.viewmodel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import launcher.focux.datastore.app.InstalledPackage
import launcher.focux.datastore.app.applicationDatastore
import launcher.focux.datastore.lockedapp.LockedApp
import launcher.focux.datastore.lockedapp.LockedAppModel
import launcher.focux.datastore.lockedapp.LockedAppRepo
import launcher.focux.datastore.lockedapp.lockedAppDatastore
import launcher.focux.datastore.pinnedapp.PinnedApp
import launcher.focux.datastore.pinnedapp.PinnedAppModel
import launcher.focux.datastore.pinnedapp.pinnedAppModelDatastore
import launcher.focux.datastore.userpreference.PreferenceModel
import launcher.focux.datastore.userpreference.PreferenceRepo
import launcher.focux.datastore.userpreference.preferenceDatastore

class SettingViewmodel(application : Application) : AndroidViewModel(application) {

    val setting : StateFlow<PreferenceModel> = application.preferenceDatastore.data.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = PreferenceModel()
    )

    val font = application.preferenceDatastore.data

    val pinnedApps : StateFlow<PinnedAppModel> = application.pinnedAppModelDatastore.data.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = emptyList<PinnedApp>()
    ) as StateFlow<PinnedAppModel>

    val packages = application.applicationDatastore.data.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        InstalledPackage()
    )

    val lockedApps: StateFlow<List<LockedApp>> = LockedAppRepo(application).lockedApps.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList<LockedApp>()
    )

    private val _showTimer = MutableStateFlow(false)
    val showTimer = _showTimer.asStateFlow()

    fun toggleTimer() {
        _showTimer.value = !_showTimer.value
    }
}