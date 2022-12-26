package com.nisum.challenge.app.repositories

import com.nisum.challenge.common.database.FakePokemonDao
import com.nisum.challenge.app.services.IPokeApi
import com.nisum.challenge.common.database.PokeDao
import com.nisum.challenge.common.database.entity.PokeEntity
import com.nisum.challenge.common.database.mappers.asDomain
import com.nisum.challenge.common.database.mappers.asEntity
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.common.models.ResultSearchModel
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import com.nisum.challenge.common.networking.Variables
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.get
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class PokeRepositoryTest : KoinTest {

    @Mock
    lateinit var api: IPokeApi
    private val dao: PokeDao = FakePokemonDao()

    @BeforeTest
    fun setUp() {
        stopKoin()
        startKoin {
            modules(
                module {
                    single { PokeRepository(api, dao) }
                }
            )
        }
    }

    @Test
    fun `get data local`() = runTest {
        // Given
        val localData = List(10) { i -> PokeEntity(i.toString(), i.toString()) }
        dao.insert(localData)
        // When
        val result = get<PokeRepository>().get()
        // Then
        result.map {
            assertTrue(it is Success)
            assertEquals(localData.asDomain(), it.data.items)
        }
    }

    @Test
    fun `get data network and insert local`() = runTest {
        // Given
        Variables.isNetworkConnected = true
        val networkData =
            List(10) { i -> PokeModel((i * 2).toString(), (i * 2).toString()) }
        `when`(api.get()).thenReturn(Success(ResultSearchModel(20, networkData), 200))
        // When
        val result = get<PokeRepository>()
            .get()
            .take(2)
            .toList(mutableListOf())
        // Then
        assertTrue(result[0] is Loading)
        assertTrue(result[1] is Success)
        with(result[1]) {
            assertEquals(networkData, data?.items)
            dao
                .all()
                .map { local ->
                    assertEquals(local, data?.items?.asEntity())
                }
        }
    }
}
