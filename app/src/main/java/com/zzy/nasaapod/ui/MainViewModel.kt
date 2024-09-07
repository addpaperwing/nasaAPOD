package com.zzy.nasaapod.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.data.repository.APODRepository
import com.zzy.nasaapod.domain.GetRemoteAPODsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.launch

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: APODRepository,
    getRemoteAPODsUseCase: GetRemoteAPODsUseCase
): ViewModel() {

    private val _page: MutableStateFlow<Int> = MutableStateFlow(0)

    val savedAPODs: StateFlow<List<APOD>> = repository.getSavedAPODs().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    val getAPODsState: StateFlow<UiState<List<APOD>>> = _page.flatMapLatest {
        getRemoteAPODsUseCase()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading,
    )

    fun loadMore() {
        _page.value += 1
    }

    fun onLikeStateChanged(apod: APOD, isLike: Boolean) {
        viewModelScope.launch {
            repository.onAPODLikeStateChange(apod, isLike)
        }
    }
}