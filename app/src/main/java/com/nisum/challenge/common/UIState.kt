package com.nisum.challenge.common

import com.nisum.challenge.common.models.PokeModel

data class UIState(
    val loading: Boolean = false,
    val list: List<PokeModel>,
    val error: Boolean = false,
    val empty: Boolean = false
)