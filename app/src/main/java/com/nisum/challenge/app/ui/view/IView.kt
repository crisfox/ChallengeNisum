package com.nisum.challenge.app.ui.view

interface IView<T, E> {
    fun render(state: T)
    fun onEvent(event: E)
}