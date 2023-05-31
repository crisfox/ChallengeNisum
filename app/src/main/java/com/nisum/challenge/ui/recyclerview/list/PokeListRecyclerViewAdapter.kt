package com.nisum.challenge.ui.recyclerview.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.nisum.challenge.ui.view.common.getLastPath
import com.nisum.challenge.data.model.PokeModel
import com.nisum.challenge.databinding.ItemListContentBinding

/**
 * Adapter para configurar el recycler correspondiente.
 *
 * @property items MutableList<PokeModel>? lista editable.
 */
class PokeListRecyclerViewAdapter(
    var items: MutableList<PokeModel>? = mutableListOf()
) : RecyclerView.Adapter<PokeViewHolder>() {

    private var itemsSearch: MutableList<PokeModel> = mutableListOf()

    override fun onBindViewHolder(holder: PokeViewHolder, position: Int) {
        holder.bindTo(items?.get(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokeViewHolder {
        val binding = ItemListContentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PokeViewHolder(binding)
    }

    override fun getItemCount() = items?.size ?: 0

    fun setListSearch(pokeList: List<PokeModel>) {
        this.itemsSearch = mutableListOf()
        this.itemsSearch.addAll(pokeList)
    }

    fun updateItems(pokeList: List<PokeModel>) {
        val diffCallback = PokeDiffCallback(items, pokeList)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.items?.removeAll(items ?: listOf())
        this.items?.addAll(pokeList)
        diffResult.dispatchUpdatesTo(this)
    }

    /**
     * Filtro y actualización de listados.
     *
     * @param text String texto a buscar.
     */
    fun handlePokemonSearchAction(text: String) {
        val searchResultsPokemonList = mutableListOf<PokeModel>()
        val searchEntry = text
            .trim()
            .lowercase()
        updateItems(mutableListOf())
        if (searchEntry.isNotEmpty()) {
            itemsSearch.map {
                if (it.name == searchEntry || it.name.contains(searchEntry) || it.url.getLastPath() == searchEntry) {
                    searchResultsPokemonList.add(it)
                }
                updateItems(searchResultsPokemonList)
            }
        } else {
            updateItems(itemsSearch)
        }
    }
}
