package com.zzy.nasaapod.data.repository

import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.Api
import com.zzy.nasaapod.data.remote.UiState
import javax.inject.Inject

class DefaultAPODRepository @Inject constructor(
    private val api: Api,
): APODRepository {

    override suspend fun getAPODs(): List<APOD> = api.getAPODs()
}