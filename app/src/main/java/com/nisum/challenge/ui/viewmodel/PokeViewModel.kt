package com.nisum.challenge.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.nisum.challenge.domain.model.PokeModel
import com.nisum.challenge.data.network.model.Loading
import com.nisum.challenge.data.network.model.Success
import com.nisum.challenge.domain.GetListPokeUseCase
import com.nisum.challenge.ui.view.common.Message
import com.nisum.challenge.ui.view.common.UIEvent
import com.nisum.challenge.ui.view.common.UIState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Mantiene los datos persistentes en el viewModelScope y envia eventos para que la view pueda tomar desiciones.
 */
internal class PokeViewModel(
    private val getListPokeUseCase: GetListPokeUseCase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<UIState<List<PokeModel>>, UIEvent>() {

    override val initialState
        get() = UIState<List<PokeModel>>(true, listOf())

    fun refresh() {
        fetchPokes()
    }

    fun fetchPokes() {
        viewModelScope.launch(dispatcher) {
            getListPokeUseCase
                .invoke()
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
