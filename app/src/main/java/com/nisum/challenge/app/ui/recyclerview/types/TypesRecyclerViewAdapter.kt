package com.nisum.challenge.app.ui.recyclerview.types

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.common.models.TypeResponse
import com.nisum.challenge.databinding.ItemChipBinding

/**
 * Adapter paginado con comparación de datos para optimizar procesos.
 */
class TypesRecyclerViewAdapter(
    var items: MutableList<TypeResponse>? = mutableListOf()
) : RecyclerView.Adapter<TypeViewHolder>() {

    override fun onBindViewHolder(holder: TypeViewHolder, position: Int) {
        holder.bindTo(items?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeViewHolder {
        val binding = ItemChipBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TypeViewHolder(binding)
    }

    override fun getItemCount() = items?.size ?: 0

    fun updateItems(list: List<TypeResponse>) {
        val diffCallback = TypeDiffCallback(items, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items?.removeAll(items ?: listOf())
        this.items?.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }
}
