package com.nisum.challenge.ui.view.common

/**
 * Interface de la vista para seguir lineamientos.
 *
 * @param T Tipo del estado.
 * @param E Tipo del evento.
 */
interface StandardView<T, E> {
    fun render(state: T)
    fun onEvent(event: E)
}