package com.zzy.nasaapod.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.data.repository.APODRepository
import com.zzy.nasaapod.domain.GetMoreAPODsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import java.util.ArrayList

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(getMoreAPODsUseCase: GetMoreAPODsUseCase): ViewModel() {

    private val _page: MutableStateFlow<Int> = MutableStateFlow(0)

    @OptIn(ExperimentalCoroutinesApi::class)
    val getAPODsState: StateFlow<UiState<List<APOD>>> = _page.flatMapLatest {
        getMoreAPODsUseCase()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading,
    )

//    val apods: StateFlow<List<APOD>> = getAPODsState.map {
//        if (it is UiState.Success) {
//            it.data
//        } else {
//            emptyList()
//        }
//    }.scan(ArrayList<APOD>()) { list, result ->
//        list.addAll(result)
//        list
//    }.stateIn(
//        scope = viewModelScope,
//        started = SharingStarted.WhileSubscribed(5_000),
//        initialValue = emptyList(),
//    )

    fun loadMore() {
        _page.value += 1
    }
}