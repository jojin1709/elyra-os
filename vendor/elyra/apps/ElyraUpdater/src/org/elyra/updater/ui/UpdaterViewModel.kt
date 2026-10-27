package org.elyra.updater.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.elyra.updater.data.UpdateRepository
import org.elyra.updater.model.UpdateInfo
import org.elyra.updater.model.UpdateStatus

class UpdaterViewModel(private val repository: UpdateRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(UpdateInfo())
    val uiState: StateFlow<UpdateInfo> = _uiState.asStateFlow()

    init {
        loadStatus()
    }

    fun loadStatus() {
        viewModelScope.launch {
            _uiState.value = repository.getInitialInfo()
        }
    }

    fun checkForUpdates() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = UpdateStatus.CHECKING)
            val result = repository.checkForUpdates()
            _uiState.value = result
        }
    }

    fun installUpdate() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(status = UpdateStatus.DOWNLOADING, downloadProgress = 0f)
            
            // Progressive download simulation
            for (p in 1..10) {
                delay(300)
                _uiState.value = _uiState.value.copy(downloadProgress = p / 10f)
            }

            _uiState.value = _uiState.value.copy(status = UpdateStatus.INSTALLING)
            delay(1000)

            _uiState.value = _uiState.value.copy(status = UpdateStatus.RESTART_REQUIRED)
        }
    }
}
