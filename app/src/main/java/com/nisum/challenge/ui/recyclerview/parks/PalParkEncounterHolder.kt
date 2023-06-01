package com.nisum.challenge.ui.recyclerview.parks

import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.domain.model.PalParkEncounter
import com.nisum.challenge.databinding.ItemChipBinding

/**
 * Se encarga de mostrar los datos necesarios para la vista.
 */
class PalParkEncounterHolder(private val binding: ItemChipBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bindTo(item: PalParkEncounter?) {
        item?.let { binding.chip.text = item.area.name.replaceFirstChar { it.uppercase() } }
    }
}
