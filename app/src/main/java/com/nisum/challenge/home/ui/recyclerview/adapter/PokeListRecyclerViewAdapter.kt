package com.nisum.challenge.home.ui.recyclerview.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.databinding.ItemListContentBinding
import com.nisum.challenge.home.ui.recyclerview.viewholder.PokeViewHolder

/**
 * Adapter paginado con comparación de datos para optimizar procesos.
 */
class PokeListRecyclerViewAdapter(var items: List<PokeModel>? = listOf()) : RecyclerView.Adapter<PokeViewHolder>() {

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.bindTo(items?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeViewHolder(binding)
    }

    override fun getItemCount() = items?.size ?: 0

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<PokeModel>() {
            override fun areItemsTheSame(oldItem: PokeModel, newItem: PokeModel): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(oldItem: PokeModel, newItem: PokeModel): Boolean {
                return oldItem == newItem
            }
        }
    }
}
