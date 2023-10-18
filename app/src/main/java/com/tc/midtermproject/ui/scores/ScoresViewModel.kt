package com.tc.midtermproject.ui.scores

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tc.midtermproject.data.model.sat.SatItemModel
import com.tc.midtermproject.data.repository.service.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScoresViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {

    private val _schoolScores = MutableStateFlow(SatItemModel())
    val schoolScores: StateFlow<SatItemModel> = _schoolScores

    fun getScores(dbn: String) {
        var response: List<SatItemModel> = emptyList()
        viewModelScope.launch {
            response = repository.getSat()
            _schoolScores.emit(
                response.find { it.dbn == dbn } ?: SatItemModel(schoolName = "School Not Found")
            )
        }
    }
}