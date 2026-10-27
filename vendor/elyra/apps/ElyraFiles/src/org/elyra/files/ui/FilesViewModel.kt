package org.elyra.files.ui

import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.elyra.files.data.FilesRepository
import org.elyra.files.data.StorageStats
import org.elyra.files.model.FileItem

data class FilesUiState(
    val storageStats: StorageStats = StorageStats(0, 0, 0, 0),
    val currentPath: String = Environment.getExternalStorageDirectory().absolutePath,
    val items: List<FileItem> = emptyList(),
    val isVaultLocked: Boolean = true
)

class FilesViewModel(private val repository: FilesRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(FilesUiState())
    val uiState: StateFlow<FilesUiState> = _uiState.asStateFlow()

    init {
        loadData()
    }

    fun loadData() {
        viewModelScope.launch {
            val stats = repository.getStorageStats()
            val files = repository.listFiles(_uiState.value.currentPath)
            _uiState.value = _uiState.value.copy(
                storageStats = stats,
                items = files
            )
        }
    }

    fun navigateTo(path: String) {
        viewModelScope.launch {
            val files = repository.listFiles(path)
            _uiState.value = _uiState.value.copy(
                currentPath = path,
                items = files
            )
        }
    }
}
