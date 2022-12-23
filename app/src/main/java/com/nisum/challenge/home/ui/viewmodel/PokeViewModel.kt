package com.nisum.challenge.home.ui.viewmodel

import androidx.lifecycle.viewModelScope
import com.nisum.challenge.common.Message
import com.nisum.challenge.common.UIEvent
import com.nisum.challenge.common.UIState
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import com.nisum.challenge.home.repositories.PokeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Mantiene los datos persistentes en el viewModelScope
 */
internal class PokeViewModel(
    private val repository: PokeRepository,
) : BaseViewModel<UIState, UIEvent>() {

    override val initialState
        get() = UIState(true, listOf())

    private var loading = false
    private var list = listOf<PokeModel>()
    private var error = false
    private var empty = false

    fun refresh() {
        fetchPokes()
    }

    fun fetchPokes() {
        viewModelScope.launch(Dispatchers.IO) {
            repository
                .get()
                .collectLatest { result ->
                    when (result) {
                        is Success -> {
                            loading = false
                            error = false
                            empty = result.data.items.isEmpty()
                            list = result.data.items
                        }
                        is Loading -> {
                            loading = true
                            error = false
                            empty = false
                            list = result.data?.items ?: listOf()
                        }
                        else -> {
                            loading = false
                            error = true
                            empty = false
                            pushEvent(Message(result.message))
                        }
                    }
                    invalidate()
                }
        }
    }

    private fun invalidate() {
        updateState(
            UIState(
                loading = loading,
                list = list,
                error = error,
                empty = empty
            )
        )
    }
}