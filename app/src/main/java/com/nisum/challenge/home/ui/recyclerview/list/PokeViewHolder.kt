package com.nisum.challenge.home.ui.recyclerview.list

import android.os.Bundle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.R
import com.nisum.challenge.common.getLasPath
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.databinding.ItemListContentBinding
import com.nisum.challenge.home.ui.view.DetailFragment
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

    fun bindTo(item: PokeModel?) {
        item?.let {
            binding.idText.text = it.name.replaceFirstChar { it.uppercase() }
            Picasso
                .get()
                .load("$BASE_URL_IMAGE${item.url.getLasPath()}.$FORMAT_PNG")
                .into(binding.imageAvatar)
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
}
