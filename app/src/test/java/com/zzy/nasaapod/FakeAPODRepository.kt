package com.zzy.nasaapod


import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.data.repository.APODRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flow
import okio.IOException

class FakeAPODRepository: APODRepository {

    private var isRemoteRequestSuccessful = true
    private var page = 1

    private val fakeRemoteData1 = listOf(
        APOD("1", title = "apod", localPath = null),
        APOD("2", title = "apod", localPath = null),
        APOD("3", title = "apod", localPath = null),
        APOD("4", title = "apod", localPath = null),
        APOD("5", title = "apod", localPath = null),
        APOD("6", title = "apod", localPath = null),
        APOD("7", title = "apod", localPath = null),
        APOD("8", title = "apod", localPath = null),
    )
    private val fakeRemoteData2 = listOf(
        APOD("9", title = "apod", localPath = null),
        APOD("10", title = "apod", localPath = null),
        APOD("11", title = "apod", localPath = null),
        APOD("12", title = "apod", localPath = null),
        APOD("13", title = "apod", localPath = null),
        APOD("14", title = "apod", localPath = null),
        APOD("15", title = "apod", localPath = null),
        APOD("16", title = "apod", localPath = null),
    )

    private val fakeError = IOException("")

    private val fakeLocalSavedData = HashMap<String, APOD>()
    private val savedDataFlow = MutableSharedFlow<List<APOD>>()

    fun setRemoteRequestSuccessful(isSuccessful: Boolean) {
        isRemoteRequestSuccessful = isSuccessful
    }

    fun nextPage() {
        page = 2
    }

    override fun getNewAPODs(): Flow<UiState<List<APOD>>> = flow {
        emit(UiState.Loading)
        if (isRemoteRequestSuccessful) {
            emit(UiState.Success(if (page == 1) {
                fakeRemoteData1
            } else {
                fakeRemoteData2
            }))
        } else {
            emit(UiState.Error(fakeError))
        }
    }


    override suspend fun onAPODLikeStateChange(apod: APOD, isLike: Boolean) {
        if (isLike) {
            apod.localPath = "fake local path"
            fakeLocalSavedData[apod.date] = apod
            savedDataFlow.emit(fakeLocalSavedData.values.toList())
        } else {
            apod.localPath = null
            fakeLocalSavedData.remove(apod.date)
            savedDataFlow.emit(fakeLocalSavedData.values.toList())
        }
    }

    override fun getSavedAPODs(): Flow<List<APOD>> = savedDataFlow
}