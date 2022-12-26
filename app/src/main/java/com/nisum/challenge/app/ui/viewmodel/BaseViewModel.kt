package com.nisum.challenge.app.ui.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow

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