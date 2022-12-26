package com.nisum.challenge.home.ui.recyclerview.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.databinding.ItemListContentBinding

/**
 * Adapter paginado con comparación de datos para optimizar procesos.
 */
class PokeListRecyclerViewAdapter(
    var items: MutableList<PokeModel>? = mutableListOf()
) : RecyclerView.Adapter<PokeViewHolder>() {

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.bindTo(items?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeViewHolder(binding)
    }

    override fun getItemCount() = items?.size ?: 0

    fun updateItems(pokeList: List<PokeModel>) {
        val diffCallback = PokeDiffCallback(items, pokeList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items?.removeAll(items ?: listOf())
        this.items?.addAll(pokeList)
        diffResult.dispatchUpdatesTo(this)
    }
}
