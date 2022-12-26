package com.nisum.challenge.home.ui.recyclerview.types

import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.common.models.TypeResponse
import com.nisum.challenge.databinding.ItemChipBinding

/**
 * Se encarga de mostrar los datos necesarios para la vista.
 */
class TypeViewHolder(private val binding: ItemChipBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bindTo(item: TypeResponse?) {
        item?.let { binding.chip.text = item.type.name.replaceFirstChar { it.uppercase() } }
    }
}
