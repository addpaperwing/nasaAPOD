package com.zzy.nasaapod.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.zzy.nasaapod.data.model.APOD
import kotlinx.coroutines.flow.asStateFlow
import com.zzy.nasaapod.data.repository.APODRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import okio.IOException
import java.net.UnknownHostException

import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: APODRepository,
): ViewModel() {

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _apods: MutableStateFlow<List<APOD>> = MutableStateFlow(emptyList())
    val apods: StateFlow<List<APOD>> = _apods.asStateFlow()


    val savedAPODs: StateFlow<List<APOD>> = repository.getSavedAPODs().stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = emptyList()
    )


    fun loadMore() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val newApods = repository.getNewAPODs()
                _apods.value += newApods
            } catch (e: HttpException) {

            } catch (e: IOException) {

            }
            _isLoading.value = false
        }
    }

    fun onLikeStateChanged(apod: APOD, isLike: Boolean) {
        viewModelScope.launch {
            repository.onAPODLikeStateChange(apod, isLike)
        }
    }
}