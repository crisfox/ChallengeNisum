package com.nisum.challenge.app.ui.recyclerview.types

import androidx.recyclerview.widget.DiffUtil
import com.nisum.challenge.common.models.TypeResponse

class TypeDiffCallback(
    private val oldList: List<TypeResponse>? = listOf(),
    private val newList: List<TypeResponse>? = listOf(),
) : DiffUtil.Callback() {

    override fun getOldListSize() = oldList?.size ?: 0

    override fun getNewListSize() = newList?.size ?: 0

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList?.get((oldItemPosition))?.type?.name == newList?.get((oldItemPosition))?.type?.name

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList?.get((oldItemPosition)) == newList?.get((oldItemPosition))
}
