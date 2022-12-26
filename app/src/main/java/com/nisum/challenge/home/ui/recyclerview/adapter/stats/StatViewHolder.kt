package com.nisum.challenge.home.ui.recyclerview.adapter.stats

import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.common.models.BaseStat
import com.nisum.challenge.common.models.Type
import com.nisum.challenge.databinding.ItemChipBinding
import com.nisum.challenge.databinding.ItemStatBinding

/**
 * Se encarga de mostrar los datos necesarios para la vista.
 */
class StatViewHolder(private val binding: ItemStatBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bindTo(item: BaseStat?) {
        item?.let {
            binding.name.text = item.stat.name.mapperStat().uppercase()
            binding.progress.progress = item.baseStat
        }
    }
}

fun String.mapperStat() = when(this){
    "attack" -> "ATK"
    "defense" -> "DEF"
    "special-attack" -> "S-ATK"
    "special-defense" -> "S-DEF"
    "speed" -> "SPD"
    else -> this
}
