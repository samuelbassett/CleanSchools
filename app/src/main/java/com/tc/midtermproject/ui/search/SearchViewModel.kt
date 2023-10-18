package com.tc.midtermproject.ui.search

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
class SearchViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    private val _searchData = MutableStateFlow<List<SchoolItemModel>>(emptyList())
    val searchData: StateFlow<List<SchoolItemModel>> = _searchData

    fun getSearch(query: String) {
        var response: List<SchoolItemModel> = emptyList()
        viewModelScope.launch {
            response = repository.getSchools()
            if (!response.isNullOrEmpty()) {
                val filteredList = response.filter { item ->
                    listOf(
                        item.dbn,
                        item.schoolName,
                        item.overviewParagraph,
                        item.location,
                        item.city,
                        item.interest1,
                        item.interest2,
                        item.interest3,
                        item.interest4,
                        item.interest5,
                        item.neighborhood,
                        item.schoolSports,
                        item.extracurricularActivities
                    ).any { property ->
                        property?.contains(query, ignoreCase = true) == true
                    }
                }
                _searchData.emit(filteredList)
            }
        }
    }
}