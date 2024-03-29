package com.nisum.challenge.ui.viewmodel

import com.nisum.challenge.ui.view.common.Message
import com.nisum.challenge.ui.view.common.UIState
import com.nisum.challenge.domain.model.PokeModel
import com.nisum.challenge.domain.model.ResultSearchModel
import com.nisum.challenge.data.network.model.AppNetworkResult
import com.nisum.challenge.data.network.model.Loading
import com.nisum.challenge.data.network.model.Success
import com.nisum.challenge.data.network.model.Unsuccessful
import com.nisum.challenge.data.repositories.PokeRepository
import com.nisum.challenge.domain.GetListPokeUseCase
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

    lateinit var getListPokeUseCase: GetListPokeUseCase

    @Mock
    lateinit var repository: PokeRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @BeforeTest
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getListPokeUseCase = GetListPokeUseCase(repository)
        stopKoin()
        startKoin {
            modules(
                module {
                    single { PokeViewModel(getListPokeUseCase, UnconfinedTestDispatcher()) }
                }
            )
        }
    }

    @Test
    fun `fetch get list use case success should state loading and success`() = runTest {
        // Given
        val dataChannel = Channel<AppNetworkResult<ResultSearchModel>>(1)
        val data = ResultSearchModel(items = List(10) { i -> PokeModel(i.toString(), i.toString()) })

        `when`(getListPokeUseCase.invoke()).thenReturn(dataChannel.consumeAsFlow())
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
    fun `fetch get list use case get error should send a EventUI`() = runTest {
        // Given
        val dataChannel = Channel<AppNetworkResult<ResultSearchModel>>(1)
        `when`(getListPokeUseCase.invoke()).thenReturn(dataChannel.consumeAsFlow())
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
