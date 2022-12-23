package com.nisum.challenge.home.ui.recyclerview.viewholder

import android.os.Bundle
import androidx.core.net.toUri
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.R
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.databinding.ItemListContentBinding
import com.nisum.challenge.home.ui.ItemDetailFragment
import com.squareup.picasso.Picasso

/**
 * Se encarga de mostrar los datos necesarios para la vista.
 */
class PokeViewHolder(private val binding: ItemListContentBinding) : RecyclerView.ViewHolder(
    binding.root
) {

    companion object {
        const val BASE_URL_IMAGE = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/"
        const val FORMAT_PNG = "png"
    }

    fun bindTo(pokeModel: PokeModel?) {
        pokeModel?.let {
            binding.idText.text = it.name
            Picasso
                .get()
                .load("$BASE_URL_IMAGE${it.url?.toUri()?.lastPathSegment}.$FORMAT_PNG")
                .into(binding.imageAvatar)
        }

        with(itemView) {
            tag = pokeModel
            binding.item.setOnClickListener {
                val itemSelected = tag as PokeModel
                val bundle = Bundle()
                bundle.putParcelable(
                    ItemDetailFragment.ARG_ITEM_PARCELABLE,
                    itemSelected
                )
                findNavController()
                    .navigate(R.id.show_item_detail, bundle)
            }
        }
    }
}
