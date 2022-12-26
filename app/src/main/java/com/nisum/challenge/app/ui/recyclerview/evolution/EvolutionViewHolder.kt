package com.nisum.challenge.app.ui.recyclerview.evolution

import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.common.getLasPath
import com.nisum.challenge.common.models.SpeciesName
import com.nisum.challenge.databinding.ItemImageBinding
import com.nisum.challenge.app.ui.recyclerview.list.PokeViewHolder
import com.squareup.picasso.Picasso

/**
 * Se encarga de mostrar los datos necesarios para la vista.
 */
class EvolutionViewHolder(private val binding: ItemImageBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bindTo(item: SpeciesName?) {

        item?.let {
            binding.name.text = item.name.replaceFirstChar { it.uppercase() }
            Picasso
                .get()
                .load("${PokeViewHolder.BASE_URL_IMAGE}${item.url.getLasPath()}.${PokeViewHolder.FORMAT_PNG}")
                .into(binding.image)
        }
    }
}
