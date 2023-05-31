package com.nisum.challenge.ui.recyclerview.parks

import androidx.recyclerview.widget.DiffUtil
import com.nisum.challenge.data.model.PalParkEncounter

class PalParkEncounterDiffCallback(
    private val oldList: List<PalParkEncounter>? = listOf(),
    private val newList: List<PalParkEncounter>? = listOf(),
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList?.size ?: 0

    override fun getNewListSize() = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList?.get((oldItemPosition))?.area?.name == newList?.get((oldItemPosition))?.area?.name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList?.get((oldItemPosition)) == newList?.get((oldItemPosition))
}