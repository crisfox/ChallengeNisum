package com.nisum.challenge.home.ui.view

interface IView<T, E> {
    fun render(state: T)
    fun onEvent(event: E)
}