package com.nisum.challenge.app.ui.view

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
import com.nisum.challenge.app.ui.recyclerview.list.PokeListRecyclerViewAdapter
import com.nisum.challenge.app.ui.viewmodel.PokeViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Muestra el listado que llega desde los eventos que lanza el viewmodel.
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

    /**
     * Suscripción a los eventos y estados que envia el viewmodel.
     */
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

    /**
     * Manejo de eventos de error y desiciones que toma la vista como por ejemplo mostrar el error y el mensaje.
     *
     * @param event UIEvent
     */
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
     * Configura la visibilidad y muestra los datos en la vista.
     */
    override fun render(state: UIState<List<PokeModel>>) {
        Log.d(TAG, "render: $state")
        Log.d(TAG, "list: ${state.data}")
        searchAll = state.data ?: listOf()
        adapterRecyclerView.updateItems(state.data ?: listOf())
        adapterRecyclerView.setListSearch(state.data ?: listOf())
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

    /**
     * Escucha los cambios de texto que ocasiona el buscador.
     */
    private fun setupSearch() {
        binding.search.doOnTextChanged { text, _, _, _ ->
            binding.search.clearFocus()
            adapterRecyclerView.handlePokemonSearchAction(text.toString())
            binding.emptyState.isVisible = adapterRecyclerView.items?.isEmpty() ?: false
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
