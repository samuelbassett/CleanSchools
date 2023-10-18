package com.tc.midtermproject.ui.schoollist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc.midtermproject.data.model.schools.SchoolItemModel
import com.tc.midtermproject.data.repository.service.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SchoolsViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _schoolData = MutableStateFlow<List<SchoolItemModel>>(emptyList())
    val schoolData: StateFlow<List<SchoolItemModel>> = _schoolData

    init {
        getSchools()
    }

    fun getSchools() {
        viewModelScope.launch {
            val response = repository.getSchools()
            _schoolData.emit(response ?: emptyList())
        }
    }
}