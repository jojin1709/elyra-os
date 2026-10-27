package org.elyra.privacycenter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.elyra.privacycenter.data.PrivacyRepository
import org.elyra.privacycenter.model.PrivacyState

class PrivacyCenterViewModel(private val repository: PrivacyRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(PrivacyState())
    val uiState: StateFlow<PrivacyState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = repository.getPrivacyState()
        }
    }

    fun toggleCamera(enabled: Boolean) {
        viewModelScope.launch {
            repository.setSensorEnabled(2, enabled)
            _uiState.value = _uiState.value.copy(isCameraEnabled = enabled)
        }
    }

    fun toggleMicrophone(enabled: Boolean) {
        viewModelScope.launch {
            repository.setSensorEnabled(1, enabled)
            _uiState.value = _uiState.value.copy(isMicEnabled = enabled)
        }
    }
}
