package com.nisum.challenge.common

data class UIState<D>(
    val loading: Boolean = false,
    val data: D? = null,
    val error: Boolean = false,
    val empty: Boolean = false
)