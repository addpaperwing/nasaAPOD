package com.zzy.nasaapod.data.repository

import com.zzy.nasaapod.data.local.APODDao
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.Api
import com.zzy.nasaapod.data.remote.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class DefaultAPODRepository @Inject constructor(
    private val api: Api,
    private val dao: APODDao,
): APODRepository {

    override fun getNewAPODs(): Flow<UiState<List<APOD>>> = flow<UiState<List<APOD>>> {
        emit(UiState.Success(api.getAPODs().filter { it.isImage() }))
    }.onStart {
        emit(UiState.Loading)
    }.catch {
        it.printStackTrace()
        emit(UiState.Error(it))
    }

    override suspend fun onAPODLikeStateChange(apod: APOD, isLike: Boolean) {
        if (isLike) {
            dao.insert(apod)
        } else {
            dao.deleteAPOD(apod)
        }
    }

    override fun getSavedAPODs(): Flow<List<APOD>> = dao.getSavedAPOD()
}