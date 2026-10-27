package org.elyra.launcher.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.elyra.launcher.data.AppRepository
import org.elyra.launcher.model.AppItem

data class LauncherUiState(
    val allApps: List<AppItem> = emptyList(),
    val filteredApps: List<AppItem> = emptyList(),
    val dockApps: List<AppItem> = emptyList(),
    val searchQuery: String = "",
    val isAppDrawerOpen: Boolean = false,
    val isAmoledDrawer: Boolean = true,
    val gridColumns: Int = 4
)

class LauncherViewModel(private val repository: AppRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(LauncherUiState())
    val uiState: StateFlow<LauncherUiState> = _uiState.asStateFlow()

    init {
        loadApps()
    }

    fun loadApps() {
        viewModelScope.launch {
            val apps = repository.getInstalledApps()
            val dock = apps.take(4)
            _uiState.value = _uiState.value.copy(
                allApps = apps,
                filteredApps = apps,
                dockApps = dock
            )
        }
    }

    fun updateSearchQuery(query: String) {
        val trimmed = query.trim()
        val filtered = if (trimmed.isEmpty()) {
            _uiState.value.allApps
        } else {
            _uiState.value.allApps.filter {
                it.label.contains(trimmed, ignoreCase = true) || it.packageName.contains(trimmed, ignoreCase = true)
            }
        }
        _uiState.value = _uiState.value.copy(
            searchQuery = query,
            filteredApps = filtered
        )
    }

    fun setAppDrawerOpen(open: Boolean) {
        _uiState.value = _uiState.value.copy(isAppDrawerOpen = open)
    }

    fun launchApp(app: AppItem) {
        repository.launchApp(app)
        setAppDrawerOpen(false)
    }
}
