package com.zzy.nasaapod.data.repository

import com.zzy.nasaapod.data.local.APODDao
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.Api
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import okio.IOException
import javax.inject.Inject

class DefaultAPODRepository @Inject constructor(
    private val api: Api,
    private val dao: APODDao,
): APODRepository {

    override suspend fun getNewAPODs(): List<APOD> {
        val remote = api.getAPODs().filter { it.isImage() }

        val local = dao.getSavedAPOD().first()

        if (local.isNotEmpty()) {
            val likeMap = local.associateBy { it.date }
            remote.forEach {
                it.localPath = likeMap[it.date]?.localPath
            }
        }

        return remote
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