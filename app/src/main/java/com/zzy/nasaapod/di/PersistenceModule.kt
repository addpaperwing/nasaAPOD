package com.zzy.nasaapod.di

import android.app.Application
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import androidx.core.content.contentValuesOf
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.zzy.nasaapod.data.local.APODDao
import com.zzy.nasaapod.data.local.NasaAPODDataBase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    private const val DB_NAME = "APOD_DB"


    @Provides
    @Singleton
    fun provideDatabase(application: Application): NasaAPODDataBase {
        return Room
            .databaseBuilder(application, NasaAPODDataBase::class.java, DB_NAME)
            .build()
    }


    @Provides
    fun provideChampionDao(db: NasaAPODDataBase) : APODDao = db.getAPODDao()
}