package com.tc.midtermproject.ui.navigation

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tc.midtermproject.ui.search.SearchBarState

class AppNavViewModel : ViewModel() {
    private val _searchWidgetState: MutableState<SearchBarState> =
        mutableStateOf(value = SearchBarState.COLLAPSED)
    val searchWidgetState: State<SearchBarState> = _searchWidgetState

    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState

    fun updateSearchWidgetState(newValue: SearchBarState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }
}