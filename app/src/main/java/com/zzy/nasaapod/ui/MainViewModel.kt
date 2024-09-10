package com.zzy.nasaapod.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import kotlinx.coroutines.flow.asStateFlow
import com.zzy.nasaapod.data.repository.APODRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import java.net.UnknownHostException

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: APODRepository,
): ViewModel() {

    private val _page: MutableStateFlow<Int> = MutableStateFlow(0)

    val savedAPODs: StateFlow<List<APOD>> = repository.getSavedAPODs().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    val newApodsState: StateFlow<UiState<List<APOD>>> = _page.flatMapLatest {
        repository.getNewAPODs()
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = UiState.Loading
    )

    private val cachedList : ArrayList<APOD> = ArrayList()
    val apods : StateFlow<List<APOD>> = newApodsState.map {
        if (it is UiState.Success) {
            it.data
        } else {
            emptyList()
        }
    }.map {
        cachedList.addAll(it)
        cachedList
    }.combine(savedAPODs) { remote, local ->
        val likeMap = local.associateBy { it.date }
        remote.forEach {
            it.updateLocalPath(likeMap[it.date]?.localPath)
        }

        remote
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )


    fun loadMore() {
        _page.value++
    }

    fun onLikeStateChanged(apod: APOD, isLike: Boolean) {
        viewModelScope.launch {
            repository.onAPODLikeStateChange(apod, isLike)
        }
    }
}