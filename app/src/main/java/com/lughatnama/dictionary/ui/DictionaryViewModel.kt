package com.lughatnama.dictionary.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lughatnama.dictionary.data.DictionaryEntry
import com.lughatnama.dictionary.data.DictionaryRepository
import com.lughatnama.dictionary.search.SearchEngine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class DictionaryUiState(
    val query: String = "",
    val results: List<DictionaryEntry> = emptyList(),
    val isLoading: Boolean = true,
    val loadError: Boolean = false,
)

class DictionaryViewModel(
    private val repository: DictionaryRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(DictionaryUiState())
    val state: StateFlow<DictionaryUiState> = _state.asStateFlow()

    private var entries: List<DictionaryEntry> = emptyList()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching(repository::loadEntries)
                .onSuccess { loaded ->
                    entries = loaded
                    val currentQuery = _state.value.query
                    _state.value = _state.value.copy(
                        results = SearchEngine.search(loaded, currentQuery),
                        isLoading = false,
                    )
                }
                .onFailure {
                    _state.value = _state.value.copy(isLoading = false, loadError = true)
                }
        }
    }

    fun onQueryChanged(query: String) {
        _state.value = _state.value.copy(
            query = query,
            results = if (_state.value.isLoading) emptyList() else SearchEngine.search(entries, query),
        )
    }

    fun clearQuery() = onQueryChanged("")
}

class DictionaryViewModelFactory(
    private val repository: DictionaryRepository,
) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        require(modelClass.isAssignableFrom(DictionaryViewModel::class.java))
        return DictionaryViewModel(repository) as T
    }
}
