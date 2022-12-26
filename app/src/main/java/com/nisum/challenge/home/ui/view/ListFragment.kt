package com.nisum.challenge.home.ui.view

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.nisum.challenge.R
import com.nisum.challenge.common.Message
import com.nisum.challenge.common.UIEvent
import com.nisum.challenge.common.UIState
import com.nisum.challenge.common.getLasPath
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.databinding.FragmentItemListBinding
import com.nisum.challenge.home.ui.recyclerview.adapter.list.PokeListRecyclerViewAdapter
import com.nisum.challenge.home.ui.viewmodel.PokeViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Muestra el listado que llega desde el source correspondiente
 */
class ListFragment : Fragment(), IView<UIState<List<PokeModel>>, UIEvent> {

    private val viewModel by viewModel<PokeViewModel>()
    private lateinit var adapterRecyclerView: PokeListRecyclerViewAdapter
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

    private var searchAll = listOf<PokeModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecycler()
        setupButtons()
        setupSearch()
        subscribe()
        viewModel.fetchPokes()
    }

    private fun subscribe() {
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                render(it)
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.event
                .consumeAsFlow()
                .collect {
                    it?.let { event -> onEvent(event) }
                }
        }
    }

    override fun onEvent(event: UIEvent) {
        Log.d(TAG, "event: $event")
        when (event) {
            is Message -> {
                binding.errorState.isVisible = true
                binding.errorText.text = event.message
            }
        }
    }

    /**
     * Configura para que cuando reciba nueva información la envie al adapter y oculte el swipe refresh.
     */
    override fun render(state: UIState<List<PokeModel>>) {
        Log.d(TAG, "render: $state")
        Log.d(TAG, "list: ${state.data}")
        searchAll = state.data ?: listOf()
        adapterRecyclerView.updateItems(state.data ?: listOf())
        binding.swipeRefresh.isRefreshing = state.loading
        binding.progressBarHome.isVisible = state.loading
        binding.errorState.isVisible = state.error
        binding.emptyState.isVisible = state.empty
    }

    /**
     * Configura el recycler junto con el adapter.
     */
    private fun setupRecycler() {
        adapterRecyclerView = PokeListRecyclerViewAdapter()
        binding.itemList.layoutManager = GridLayoutManager(context, resources.getInteger(R.integer.grid_column_count))
        binding.itemList.adapter = adapterRecyclerView
    }

    /**
     * Configura el botón de retry y refresh.
     */
    private fun setupButtons() {
        binding.retryButtonError.setOnClickListener { viewModel.refresh() }
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
    }

    private fun setupSearch() {
        binding.search.doOnTextChanged { text, _, _, _ ->
            handlePokemonSearchAction(text.toString())
        }
    }

    private fun handlePokemonSearchAction(text: String) {
        binding.search.clearFocus()
        val searchResultsPokemonList = mutableListOf<PokeModel>()
        val searchEntry = text
            .trim()
            .lowercase()
        adapterRecyclerView.updateItems(mutableListOf())
        if (searchEntry.isNotEmpty()) {
            searchAll.map {
                if (it.name == searchEntry || it.name.contains(searchEntry) || it.url.getLasPath() == searchEntry) {
                    searchResultsPokemonList.add(it)
                }
            }
            binding.emptyState.isVisible = searchResultsPokemonList.isEmpty()
            adapterRecyclerView.updateItems(searchResultsPokemonList)
        } else {
            adapterRecyclerView.updateItems(searchAll)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
