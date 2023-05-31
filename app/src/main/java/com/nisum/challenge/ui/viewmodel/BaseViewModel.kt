package com.nisum.challenge.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

/**
 * Base abstracta del view model para seguir lineamientos.
 *
 * @param T Tipo del estado.
 * @param E Tipo del evento.
 * @property initialState T Estado inicial.
 * @property state MutableStateFlow<T> Estados.
 * @property event Channel<E?> Eventos.
 */
abstract class BaseViewModel<T, E> : ViewModel() {
    abstract val initialState: T
    val state = MutableStateFlow(initialState)
    val event = Channel<E?>(1)

    protected fun updateState(newState: T) {
        state.value = newState
    }

    protected fun pushEvent(newEvent: E) {
        event.trySend(newEvent)
    }
}
