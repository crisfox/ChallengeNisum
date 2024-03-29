package com.nisum.challenge.ui.recyclerview.parks

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.domain.model.PalParkEncounter
import com.nisum.challenge.databinding.ItemChipBinding

/**
 * Adapter para configurar el recycler correspondiente.
 *
 * @property items MutableList<PalParkEncounter>? lista editable.
 */
class PalParkEncounterRecyclerViewAdapter(
    var items: MutableList<PalParkEncounter>? = mutableListOf()
) : RecyclerView.Adapter<PalParkEncounterHolder>() {

    override fun onBindViewHolder(holder: PalParkEncounterHolder, position: Int) {
        holder.bindTo(items?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PalParkEncounterHolder {
        val binding = ItemChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PalParkEncounterHolder(binding)
    }

    override fun getItemCount() = items?.size ?: 0

    fun updateItems(list: List<PalParkEncounter>) {
        val diffCallback = PalParkEncounterDiffCallback(items, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items?.removeAll(items ?: listOf())
        this.items?.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}
