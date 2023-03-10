package com.nisum.challenge.app.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.nisum.challenge.app.repositories.IPokeRepository
import com.nisum.challenge.common.Message
import com.nisum.challenge.common.UIEvent
import com.nisum.challenge.common.UIState
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Mantiene los datos persistentes en el viewModelScope y envia eventos para que la view pueda tomar desiciones.
 */
internal class PokeViewModel(
    private val repository: IPokeRepository,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<UIState<List<PokeModel>>, UIEvent>() {

    override val initialState
        get() = UIState<List<PokeModel>>(true, listOf())

    fun refresh() {
        fetchPokes()
    }

    fun fetchPokes() {
        viewModelScope.launch(dispatcher) {
            repository
                .get()
                .collectLatest { result ->
                    when (result) {
                        is Success -> {
                            update(
                                empty = result.data.items.isEmpty(),
                                info = result.data.items
                            )
                        }
                        is Loading -> {
                            update(
                                loading = true,
                                info = result.data?.items ?: listOf()
                            )
                        }
                        else -> {
                            update(
                                error = true
                            )
                            pushEvent(Message(result.message))
                        }
                    }
                }
        }
    }

    private fun update(
        loading: Boolean = false,
        info: List<PokeModel>? = null,
        error: Boolean = false,
        empty: Boolean = false
    ) {
        updateState(
            UIState(
                loading = loading,
                data = info,
                error = error,
                empty = empty
            )
        )
    }
}
