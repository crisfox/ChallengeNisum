package com.nisum.challenge.ui.viewmodel

import com.nisum.challenge.data.repositories.PokeInfoRepository
import com.nisum.challenge.ui.view.common.Message
import com.nisum.challenge.ui.view.common.UIState
import com.nisum.challenge.data.database.mappers.asDomain
import com.nisum.challenge.data.model.PokeInfo
import com.nisum.challenge.data.model.PokeModel
import com.nisum.challenge.data.network.model.AppNetworkResult
import com.nisum.challenge.data.network.model.Loading
import com.nisum.challenge.data.network.model.Success
import com.nisum.challenge.data.network.model.Unsuccessful
import com.nisum.challenge.data.model.mockPokeInfoEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import org.mockito.kotlin.mock
import org.robolectric.RobolectricTestRunner
import kotlin.test.BeforeTest
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class PokeInfoViewModelTest : KoinTest {

    @Mock
    lateinit var repository: PokeInfoRepository

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @BeforeTest
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        stopKoin()
        startKoin {
            modules(
                module {
                    single { PokeInfoViewModel(repository, mock(), UnconfinedTestDispatcher()) }
                }
            )
        }
    }

    @Test
    fun `fetch repository success should state loading and success`() = runTest {
        // Given
        val dataChannel = Channel<AppNetworkResult<PokeInfo>>(1)
        val data = mockPokeInfoEntity().asDomain()

        `when`(repository.getInfo(anyString(), anyString())).thenReturn(dataChannel.consumeAsFlow())
        dataChannel.trySend(Loading(data))
        // When
        val viewModel = get<PokeInfoViewModel>()
        viewModel.fetchInfoPoke(PokeModel("test", "url/1"))
        //Then
        assertEquals(
            UIState(
                loading = true,
                data = data
            ),
            viewModel.state.value
        )
        dataChannel.send(Success(data, 200))
        assertEquals(
            UIState(
                data = data
            ),
            viewModel.state.first()
        )
    }

    @Test
    fun `fetch repository get error should send a EventUI`() = runTest {
        // Given
        val dataChannel = Channel<AppNetworkResult<PokeInfo>>(1)
        `when`(repository.getInfo(anyString(), anyString())).thenReturn(dataChannel.consumeAsFlow())
        dataChannel.trySend(Unsuccessful(null, 400, "error", "errorMessage"))
        // When
        val viewModel = get<PokeInfoViewModel>()
        viewModel.fetchInfoPoke(PokeModel("test", "url/1"))
        // Then
        assertEquals(
            Message(
                "errorMessage"
            ),
            viewModel.event.receive()
        )
    }
}
