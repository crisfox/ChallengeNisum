package com.nisum.challenge.ui.recyclerview.list

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou
import com.nisum.challenge.R
import com.nisum.challenge.data.model.PokeModel
import com.nisum.challenge.databinding.ItemListContentBinding
import com.nisum.challenge.ui.view.DetailFragment
import com.nisum.challenge.ui.view.common.getLastPath

/**
 * Se encarga de mostrar los datos necesarios para la vista.
 */
class PokeViewHolder(private val binding: ItemListContentBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    fun bindTo(item: PokeModel?) {
        item?.let {
            binding.idText.text = it.name.replaceFirstChar { it.uppercase() }
            GlideToVectorYou
                .init()
                .with(binding.root.context)
                .load("$BASE_URL_IMAGE_SVG${it.url.getLastPath()}.$FORMAT_SVG".toUri(), binding.imageAvatar)
        }

        with(itemView) {
            tag = item
            binding.item.setOnClickListener {
                val itemSelected = tag as PokeModel
                val bundle = Bundle()
                bundle.putParcelable(
                    DetailFragment.ARG_ITEM_PARCELABLE,
                    itemSelected
                )
                findNavController()
                    .navigate(R.id.show_item_detail, bundle)
            }
        }
    }

    companion object {
        const val BASE_URL_IMAGE_SVG =
            "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/other/dream-world/"
        const val FORMAT_SVG = "svg"
    }
}
