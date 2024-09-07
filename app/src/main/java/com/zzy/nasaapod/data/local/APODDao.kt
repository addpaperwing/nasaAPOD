package com.zzy.nasaapod.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.zzy.nasaapod.data.model.APOD
import kotlinx.coroutines.flow.Flow


@Dao
interface APODDao {

    @Query("SELECT * FROM apod")
    fun getSavedAPOD(): Flow<List<APOD>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(apod: APOD)

    @Delete
    suspend fun deleteAPOD(apod: APOD)
}