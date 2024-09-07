package com.zzy.nasaapod.domain

import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.data.repository.APODRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetMoreAPODsUseCase @Inject constructor(
    private val repository: APODRepository,
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<UiState<List<APOD>>> = flow<UiState<List<APOD>>> {
        emit(UiState.Success(repository.getAPODs().filter {
            it.isImage()
        }))
    }.onStart {
        emit(UiState.Loading)
    }.catch {
        emit(UiState.Error(it))
    }.flowOn(dispatcher)

}