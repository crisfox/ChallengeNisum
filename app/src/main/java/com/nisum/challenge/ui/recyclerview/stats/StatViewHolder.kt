package com.nisum.challenge.ui.recyclerview.stats

import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.R
import com.nisum.challenge.data.model.BaseStat
import com.nisum.challenge.databinding.ItemStatBinding

/**
 * Se encarga de mostrar los datos necesarios para la vista.
 */
class StatViewHolder(private val binding: ItemStatBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bindTo(item: BaseStat?) {
        item?.let {
            binding.name.text = item.stat.name
                .mapperStat()
                .uppercase()
            binding.progress.progress = item.baseStat
        }
    }

    private fun String.mapperStat(): String = with(binding.root.resources) {
        return when (this@mapperStat) {
            getString(R.string.attack) -> getString(R.string.atk)
            getString(R.string.defense) -> getString(R.string.def)
            getString(R.string.special_attack) -> getString(R.string.s_atk)
            getString(R.string.special_defense) -> getString(R.string.s_def)
            getString(R.string.speed) -> getString(R.string.spd)
            else -> this@mapperStat
        }
    }
}
