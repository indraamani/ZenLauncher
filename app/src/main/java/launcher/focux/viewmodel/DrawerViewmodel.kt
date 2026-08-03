package launcher.focux.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import launcher.focux.datastore.app.InstalledPackage
import launcher.focux.datastore.app.applicationDatastore
import launcher.focux.datastore.lockedapp.LockedApp
import launcher.focux.datastore.lockedapp.LockedAppRepo
import launcher.focux.datastore.userpreference.PreferenceModel
import launcher.focux.datastore.userpreference.preferenceDatastore
import launcher.focux.utils.AppModel
import launcher.focux.utils.getRecentUsedApps

class DrawerViewmodel(application: Application) : AndroidViewModel(application) {

    val packages = application.applicationDatastore.data.stateIn(
        viewModelScope,
        SharingStarted.Eagerly,
        InstalledPackage()
    )

    val setting: StateFlow<PreferenceModel> = application.preferenceDatastore.data.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Eagerly,
        initialValue = PreferenceModel()
    )

    val lockedApp: StateFlow<List<LockedApp>> = LockedAppRepo(application).lockedApps.stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList<LockedApp>()
    )

    val recentApps = getRecentUsedApps(application)
    var selectedApp = MutableStateFlow<AppModel>(AppModel())

    private var _searchText = MutableStateFlow<String>("")
    var searchText = _searchText.asStateFlow()

    private var _textFieldState = MutableStateFlow<Boolean>(false)
    var textFieldState = _textFieldState.asStateFlow()
    private var _show = MutableStateFlow(false)
    var show = _show.asStateFlow()

    private val _showTimer = MutableStateFlow(false)
    val showTimer = _showTimer.asStateFlow()

    fun toggleTimer() {
        _showTimer.value = !_showTimer.value
    }

    fun toggleShow() {
        _show.value = !_show.value
    }

    fun updateSearchText(text: String) {
        _searchText.value = text
    }

    fun toggleTextFieldState(state: Boolean) {
        _textFieldState.value = state
    }
}