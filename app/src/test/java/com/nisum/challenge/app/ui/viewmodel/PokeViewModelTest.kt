package com.nisum.challenge.app.ui.viewmodel

import com.nisum.challenge.app.repositories.IPokeRepository
import com.nisum.challenge.common.Message
import com.nisum.challenge.common.UIState
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.common.models.ResultSearchModel
import com.nisum.challenge.common.networking.AppNetworkResult
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import com.nisum.challenge.common.networking.Unsuccessful
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class PokeListViewModelTest : KoinTest {

    @Mock
    lateinit var repository: IPokeRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @BeforeTest
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        stopKoin()
        startKoin {
            modules(
                module {
                    single { PokeViewModel(repository, UnconfinedTestDispatcher()) }
                }
            )
        }
    }

    @Test
    fun `fetch repository success should state loading and success`() = runTest {
        // Given
        val dataChannel = Channel<AppNetworkResult<ResultSearchModel>>(1)
        val data = ResultSearchModel(items = List(10) { i -> PokeModel(i.toString(), i.toString()) })

        `when`(repository.get()).thenReturn(dataChannel.consumeAsFlow())
        dataChannel.trySend(Loading(data))
        // When
        val viewModel = get<PokeViewModel>()
        viewModel.fetchPokes()
        //Then
        assertEquals(
            UIState(
                loading = true,
                data = data.items
            ),
            viewModel.state.value
        )
        dataChannel.send(Success(data, 200))
        assertEquals(
            UIState(
                data = data.items
            ),
            viewModel.state.first()
        )
    }

    @Test
    fun `fetch repository get error should send a EventUI`() = runTest {
        // Given
        val dataChannel = Channel<AppNetworkResult<ResultSearchModel>>(1)
        `when`(repository.get()).thenReturn(dataChannel.consumeAsFlow())
        dataChannel.trySend(Unsuccessful(null, 400, "error", "errorMessage"))
        // When
        val viewModel = get<PokeViewModel>()
        viewModel.fetchPokes()
        // Then
        assertEquals(
            Message(
                "errorMessage"
            ),
            viewModel.event.receive()
        )
    }
}
