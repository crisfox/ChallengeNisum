package com.nisum.challenge.home.ui.recyclerview.adapter.evolution

import androidx.recyclerview.widget.DiffUtil
import com.nisum.challenge.common.models.BaseStat
import com.nisum.challenge.common.models.SpeciesName

class EvolutionDiffCallback(
    private val oldList: List<SpeciesName>? = listOf(),
    private val newList: List<SpeciesName>? = listOf(),
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList?.size ?: 0

    override fun getNewListSize() = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList?.get((oldItemPosition))?.name == newList?.get((oldItemPosition))?.name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList?.get((oldItemPosition))?.url == newList?.get((oldItemPosition))?.url
}