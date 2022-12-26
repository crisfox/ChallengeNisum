package com.nisum.challenge.home.ui.recyclerview.adapter.evolution

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.common.models.BaseStat
import com.nisum.challenge.common.models.Evolution
import com.nisum.challenge.common.models.SpeciesName
import com.nisum.challenge.databinding.ItemImageBinding
import com.nisum.challenge.databinding.ItemStatBinding

/**
 * Adapter paginado con comparación de datos para optimizar procesos.
 */
class EvolutionRecyclerViewAdapter(
    var items: MutableList<SpeciesName>? = mutableListOf()
) : RecyclerView.Adapter<EvolutionViewHolder>() {

    override fun onBindViewHolder(holder: EvolutionViewHolder, position: Int) {
        holder.bindTo(items?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EvolutionViewHolder {
        val binding = ItemImageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return EvolutionViewHolder(binding)
    }

    override fun getItemCount() = items?.size ?: 0

    fun updateItems(list: List<SpeciesName>) {
        val diffCallback = EvolutionDiffCallback(items, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items?.removeAll(items ?: listOf())
        this.items?.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}
