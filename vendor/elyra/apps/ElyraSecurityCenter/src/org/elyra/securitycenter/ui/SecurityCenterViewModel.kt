package org.elyra.securitycenter.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.elyra.securitycenter.data.SecurityRepository
import org.elyra.securitycenter.model.SecurityState

class SecurityCenterViewModel(private val repository: SecurityRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(SecurityState())
    val uiState: StateFlow<SecurityState> = _uiState.asStateFlow()

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {
            _uiState.value = repository.getSecurityState()
        }
    }
}
