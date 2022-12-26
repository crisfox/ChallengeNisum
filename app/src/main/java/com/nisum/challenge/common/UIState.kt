package com.nisum.challenge.common

/**
 * Para el manejo de los estados de la vista.
 *
 * @param D tipo de dato que utilizaría
 * @property loading Boolean estado de carga
 * @property data D? infomación a mostrar
 * @property error Boolean estado de error
 * @property empty Boolean estado de vacio
 */
data class UIState<D>(
    val loading: Boolean = false,
    val data: D? = null,
    val error: Boolean = false,
    val empty: Boolean = false
)