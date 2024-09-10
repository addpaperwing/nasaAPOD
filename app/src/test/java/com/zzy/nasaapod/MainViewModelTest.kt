package com.zzy.nasaapod

import android.provider.Contacts.Intents.UI
import com.zzy.nasaapod.data.local.APODDao
import com.zzy.nasaapod.data.model.APOD
import com.zzy.nasaapod.data.remote.Api
import com.zzy.nasaapod.data.remote.UiState
import com.zzy.nasaapod.data.repository.APODRepository
import com.zzy.nasaapod.data.repository.DefaultAPODRepository
import com.zzy.nasaapod.ui.MainViewModel
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coJustAwait
import io.mockk.coJustRun
import io.mockk.impl.annotations.MockK
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MainViewModelTest {

    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule(StandardTestDispatcher())

    private val repository: FakeAPODRepository = FakeAPODRepository()

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel(repository)
    }

    @Test
    fun savedApodIsInitiallyEmpty() = runTest {
        assertTrue(
            viewModel.savedAPODs.value.isEmpty(),
        )
    }

    @Test
    fun newApodsStateIsInitiallyLoading() = runTest {
        assertEquals(
            UiState.Loading,
            viewModel.newApodsState.value,
        )
    }

    @Test
    fun apodsStateIsInitiallyEmpty() = runTest {
        assertTrue(
            viewModel.apods.value.isEmpty(),
        )
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getRemoteData_whenLoadMoreInvoked_stateChangeFromLoadingToSuccess() = runTest {
        //Subscribe result from newApodsState
        val collectJob1 = launch { viewModel.newApodsState.collect() }
        //Load more cause newApods to emit result
        viewModel.loadMore()
        //Before we run all pending coroutines state should be loading
        assertTrue(viewModel.newApodsState.value is UiState.Loading)
        //Run all pending coroutines
        advanceUntilIdle()
        //State should be success
        assertTrue(viewModel.newApodsState.value is UiState.Success)
        //Unsubscribe
        collectJob1.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun getRemoteData_whenLoadMoreInvoked_stateChangeFromLoadingToError() = runTest {
        //Subscribe result from newApodsState
        val collectJob1 = launch { viewModel.newApodsState.collect() }
        //Set function to return a error
        repository.setRemoteRequestSuccessful(false)
        //Load more cause newApods to emit result
        viewModel.loadMore()
        //Before we run all pending coroutines state should be loading
        assertTrue(viewModel.newApodsState.value is UiState.Loading)
        //Run all pending coroutines
        advanceUntilIdle()
        //State should be Error
        assertTrue(viewModel.newApodsState.value is UiState.Error)
        //Unsubscribe
        collectJob1.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun accumulateData_whenLoadMoreTwice() = runTest {
        //Subscribe result from apods
        val collectJob1 = launch { viewModel.apods.collect() }
        //Load more cause newApods to emit result
        viewModel.loadMore()
        //Run all pending coroutines
        advanceUntilIdle()
        //newApods emit a list contains 8 items
        assertEquals(8, viewModel.apods.value.size)

        //Load more again to cause newApods to emit result again
        viewModel.loadMore()
        //Next page to let newApods to emit other 8 items
        repository.nextPage()
        //Run all pending coroutines
        advanceUntilIdle()
        //newApods emit a list contains 8 items again, apods suppose to combine all 16 items into the list
        assertEquals(16, viewModel.apods.value.size)
        //Unsubscribe
        collectJob1.cancel()
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun onLikeStateChanged_updateApods_and_savedApods() = runTest {
        //Subscribe result from apods
        val collectJob1 = launch { viewModel.apods.collect() }
        //Subscribe result from savedAPODs
        val collectJob2 = launch { viewModel.savedAPODs.collect() }
        //Load more cause newApods to emit result
        viewModel.loadMore()
        //Run all pending coroutines
        advanceUntilIdle()

        //LocalPath of every apod item should be null, cause our savedAPODs is empty
        assertTrue(viewModel.savedAPODs.value.isEmpty())
        assertTrue(viewModel.apods.value.first().localPath == null)
        assertTrue(viewModel.apods.value.random().localPath == null)

        //Like the first item
        viewModel.onLikeStateChanged(viewModel.apods.value.first(), true)
        //Run all pending coroutines
        advanceUntilIdle()

        //LocalPath of the first apod item should be non-null, cause we call like function on it will save it to savedAPODs
        assertTrue(viewModel.apods.value.first().localPath != null)
        //Check if the item add to savedAPODs
        assertEquals(viewModel.savedAPODs.value.first(), viewModel.apods.value.first())

        //Unlike the first item
        viewModel.onLikeStateChanged(viewModel.apods.value.first(), false)
        //Run all pending coroutines
        advanceUntilIdle()

        //APOD should be removed from savedAPODs
        assertTrue(viewModel.savedAPODs.value.isEmpty())
        //LocalPath of the first apod item should be set to null
        assertTrue(viewModel.apods.value.first().localPath == null)

        collectJob1.cancel()
        collectJob2.cancel()
    }
}