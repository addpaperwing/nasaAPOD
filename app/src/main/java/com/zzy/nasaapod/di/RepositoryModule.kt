package com.zzy.nasaapod.di


import com.zzy.nasaapod.data.repository.DefaultAPODRepository
import com.zzy.nasaapod.data.repository.APODRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal interface RepositoryModule {

    @Binds
    fun bindAppDataRepository(appDataRepository: DefaultAPODRepository): APODRepository

}