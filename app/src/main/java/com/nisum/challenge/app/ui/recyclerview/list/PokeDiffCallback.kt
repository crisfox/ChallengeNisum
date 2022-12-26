package com.nisum.challenge.app.ui.recyclerview.list

import androidx.recyclerview.widget.DiffUtil
import com.nisum.challenge.common.models.PokeModel

class PokeDiffCallback(
    private val oldPokeList: List<PokeModel>? = listOf(),
    private val newPokeList: List<PokeModel>? = listOf(),
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldPokeList?.size ?: 0

    override fun getNewListSize() = newPokeList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldPokeList?.get((oldItemPosition))?.url == newPokeList?.get((oldItemPosition))?.url

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldPokeList?.get((oldItemPosition))?.name == newPokeList?.get((oldItemPosition))?.name
}