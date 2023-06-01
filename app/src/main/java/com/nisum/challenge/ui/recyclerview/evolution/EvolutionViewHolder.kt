package com.nisum.challenge.ui.recyclerview.evolution

import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.nisum.challenge.ui.recyclerview.list.PokeViewHolder
import com.nisum.challenge.ui.view.common.getLastPath
import com.nisum.challenge.domain.model.SpeciesName
import com.nisum.challenge.databinding.ItemImageBinding

/**
 * Se encarga de mostrar los datos necesarios para la vista.
 */
class EvolutionViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bindTo(item: SpeciesName?) {
        item?.let {
            binding.name.text = item.name.replaceFirstChar { it.uppercase() }
            GlideToVectorYou
                .init()
                .with(binding.root.context)
                .load(
                    "${PokeViewHolder.BASE_URL_IMAGE_SVG}${it.url.getLastPath()}.${PokeViewHolder.FORMAT_SVG}".toUri(),
                    binding.image
                )
        }
    }
}
