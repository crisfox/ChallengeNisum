package com.nisum.challenge.data.repositories

import com.nisum.challenge.data.database.FakePokemonDao
import com.nisum.challenge.data.network.PokeService
import com.nisum.challenge.data.database.PokeInfoDao
import com.nisum.challenge.data.database.mappers.asDomain
import com.nisum.challenge.data.database.mappers.asEntity
import com.nisum.challenge.data.network.model.Loading
import com.nisum.challenge.data.network.model.NetworkException
import com.nisum.challenge.data.network.model.Success
import com.nisum.challenge.data.network.model.Unsuccessful
import com.nisum.challenge.data.network.Variables
import com.nisum.challenge.domain.model.mockEvolution
import com.nisum.challenge.domain.model.mockPokeInfoEntity
import com.nisum.challenge.domain.model.mockSpecies
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
import org.mockito.Mockito.anyString
import org.mockito.MockitoAnnotations
import org.robolectric.RobolectricTestRunner
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class PokeInfoRepositoryImplTest : KoinTest {

    @Mock
    lateinit var api: PokeService
    private val dao: PokeInfoDao = FakePokemonDao()

    @BeforeTest
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        stopKoin()
        startKoin {
            modules(
                module {
                    single { PokeInfoRepositoryImpl(api, dao) }
                }
            )
        }
    }

    @Test
    fun `get data local success`() = runTest {
        // Given
        val localData = mockPokeInfoEntity()
        dao.insertOne(localData)
        // When
        val result = get<PokeInfoRepositoryImpl>().getInfo("1", "test")
        // Then
        result.map {
            assertTrue(it is Success)
            assertEquals(localData.asDomain(), it.data)
        }
    }

    @Test
    fun `get data local unsuccessful`() = runTest {
        // When
        val result = get<PokeInfoRepositoryImpl>().getInfo("0", "notExist")
        // Then
        result.map {
            assertTrue(it is Unsuccessful)
            assertNull(it.data)
            assertEquals("Not found poke info.", it.message)
            assertEquals("Error database", it.error)
            assertEquals(400, it.code)
        }
    }

    @Test
    fun `get data network success and insert local`() = runTest {
        // Given
        Variables.isNetworkConnected = true
        // Given
        val localData = mockPokeInfoEntity()
        dao.insertOne(localData)
        val networkData = mockPokeInfoEntity().asDomain()
        val networkSpecies = mockSpecies()
        val networkEvolution = mockEvolution()
        `when`(api.getInfo(anyString())).thenReturn(Success(networkData, 200))
        `when`(api.getSpeciesInfo(anyString())).thenReturn(Success(networkSpecies, 200))
        `when`(api.getEvolutionInfo(anyString())).thenReturn(Success(networkEvolution, 200))

        // When
        val result = get<PokeInfoRepositoryImpl>()
            .getInfo("1", "test")
            .take(2)
            .toList(mutableListOf())
        // Then

        networkData.species = networkSpecies
        networkData.evolution = networkEvolution
        assertTrue(result[0] is Loading)
        with(result[1]) {
            assertTrue(this is Success)
            assertEquals(networkData, data)
            dao
                .get("test")
                .map { local ->
                    assertEquals(local, data.asEntity())
                }
        }
    }

    @Test
    fun `get data network unsuccessful should return Unsuccessful`() = runTest {
        // Given
        Variables.isNetworkConnected = true
        // Given
        val networkData = mockPokeInfoEntity().asDomain()
        val networkSpecies = mockSpecies()
        val networkEvolution = mockEvolution()
        `when`(api.getInfo(anyString())).thenReturn(Success(networkData, 200))
        `when`(api.getSpeciesInfo(anyString())).thenReturn(NetworkException(null, Exception(), "errorTest"))

        // When
        val result = get<PokeInfoRepositoryImpl>()
            .getInfo("1", "test")
            .take(2)
            .toList(mutableListOf())
        // Then

        networkData.species = networkSpecies
        networkData.evolution = networkEvolution
        assertTrue(result[0] is Loading)
        with(result[1]) {
            assertTrue(this is Unsuccessful)
            assertNull(data)
            assertEquals("Not found poke info.", message)
            assertEquals("Error network", error)
            assertEquals(400, code)
        }
    }
}
