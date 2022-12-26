package com.nisum.challenge.app.ui.view

/**
 * Interface de la vista para seguir lineamientos.
 *
 * @param T Tipo del estado.
 * @param E Tipo del evento.
 */
interface IView<T, E> {
    fun render(state: T)
    fun onEvent(event: E)
}