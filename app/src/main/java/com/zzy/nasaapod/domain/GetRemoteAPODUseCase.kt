package com.zzy.nasaapod.domain

import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.data.repository.APODRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class GetRemoteAPODsUseCase @Inject constructor(
    private val repository: APODRepository,
    private val dispatcher: CoroutineDispatcher
) {

    operator fun invoke(): Flow<UiState<List<APOD>>> = combine<List<APOD>, List<APOD>, UiState<List<APOD>>>(
        repository.getAPODs(),
        repository.getSavedAPODs()
    ) { remote, local ->
        val likeMap = local.associateBy { it.date }
        remote.forEach {
            it.localPath = likeMap[it.date]?.localPath
        }
        UiState.Success(remote)
    }.onStart {
        emit(UiState.Loading)
    }.catch {
        emit(UiState.Error(it))
    }.flowOn(dispatcher)


//        flow {
//        emit(repository.getAPODs().filter {
//            it.isImage()
//        })
//    }.combine(repository.getSavedAPODs().map {
//        it.associateBy { apod ->
//            apod.date
//        }
//    }) { remote, local ->
//        remote.forEach { apod ->
//            apod.localPath = local[apod.date]?.localPath
//        }
//        remote
//    }.flowOn(dispatcher)

}