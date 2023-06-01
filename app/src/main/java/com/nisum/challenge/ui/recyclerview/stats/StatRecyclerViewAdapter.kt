package com.nisum.challenge.ui.recyclerview.stats

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.domain.model.BaseStat
import com.nisum.challenge.databinding.ItemStatBinding

/**
 * Adapter para configurar el recycler correspondiente.
 *
 * @property items MutableList<BaseStat>? lista editable.
 */
class StatRecyclerViewAdapter(
    var items: MutableList<BaseStat>? = mutableListOf()
) : RecyclerView.Adapter<StatViewHolder>() {

    override fun onBindViewHolder(holder: StatViewHolder, position: Int) {
        holder.bindTo(items?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatViewHolder {
        val binding = ItemStatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StatViewHolder(binding)
    }

    override fun getItemCount() = items?.size ?: 0

    fun updateItems(list: List<BaseStat>) {
        val diffCallback = StatDiffCallback(items, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items?.removeAll(items ?: listOf())
        this.items?.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}
