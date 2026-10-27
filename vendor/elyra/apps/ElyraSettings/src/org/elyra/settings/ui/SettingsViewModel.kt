package org.elyra.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.elyra.settings.data.SettingsRepository
import org.elyra.settings.model.SettingsState

class SettingsViewModel(private val repository: SettingsRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsState())
    val uiState: StateFlow<SettingsState> = _uiState.asStateFlow()

    init {
        loadSettings()
    }

    fun loadSettings() {
        viewModelScope.launch {
            _uiState.value = repository.getSettingsState()
        }
    }

    fun toggleAmoled(enabled: Boolean) {
        _uiState.value = _uiState.value.copy(isAmoledEnabled = enabled)
    }
}
