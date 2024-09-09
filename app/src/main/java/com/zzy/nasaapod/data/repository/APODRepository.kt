package com.zzy.nasaapod.data.repository

import android.graphics.drawable.Drawable
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import kotlinx.coroutines.flow.Flow

interface APODRepository {
    fun getNewAPODs():Flow<UiState<List<APOD>>>
    suspend fun onAPODLikeStateChange(apod: APOD, isLike: Boolean)
    fun getSavedAPODs(): Flow<List<APOD>>
}