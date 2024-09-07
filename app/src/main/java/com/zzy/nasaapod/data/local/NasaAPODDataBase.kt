package com.zzy.nasaapod.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.zzy.nasaapod.data.model.APOD

@Database(
    entities = [
        APOD::class
    ],
    version = 1,
    exportSchema = false
)
abstract class NasaAPODDataBase: RoomDatabase() {
    abstract fun getAPODDao(): APODDao
}