package com.nisum.challenge.home.ui.recyclerview.stats

import androidx.recyclerview.widget.DiffUtil
import com.nisum.challenge.common.models.BaseStat

class StatDiffCallback(
    private val oldList: List<BaseStat>? = listOf(),
    private val newList: List<BaseStat>? = listOf(),
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList?.size ?: 0

    override fun getNewListSize() = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList?.get((oldItemPosition))?.stat?.name == newList?.get((oldItemPosition))?.stat?.name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList?.get((oldItemPosition)) == newList?.get((oldItemPosition))
}