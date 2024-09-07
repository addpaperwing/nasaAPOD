package com.zzy.nasaapod.data.repository

import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState

interface APODRepository {
    suspend fun getAPODs(): List<APOD>
}