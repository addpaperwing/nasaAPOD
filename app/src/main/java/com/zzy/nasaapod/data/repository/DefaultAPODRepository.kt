package com.zzy.nasaapod.data.repository

import com.zzy.nasaapod.data.local.APODDao
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.Api
import com.zzy.nasaapod.data.remote.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DefaultAPODRepository @Inject constructor(
    private val api: Api,
    private val dao: APODDao,
): APODRepository {

    override fun getAPODs(): Flow<List<APOD>> = flow {
        api.getAPODs().filter { it.isImage() }
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