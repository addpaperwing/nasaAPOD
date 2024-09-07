package com.zzy.nasaapod.domain

import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.data.repository.APODRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetLocalAPODUseCase @Inject constructor(
    private val repository: APODRepository,
) {

    operator fun invoke(): Flow<List<APOD>> = repository.getSavedAPODs()
}