package com.nisum.challenge.home.ui

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nisum.challenge.common.Message
import com.nisum.challenge.common.UIEvent
import com.nisum.challenge.common.UIState
import com.nisum.challenge.databinding.FragmentItemListBinding
import com.nisum.challenge.home.ui.recyclerview.adapter.PokeListRecyclerViewAdapter
import com.nisum.challenge.home.ui.viewmodel.PokeViewModel
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Muestra el listado que llega desde el source correspondiente
 */
class ItemListFragment : Fragment() {

    private val viewModel by viewModel<PokeViewModel>()
    private lateinit var adapterRecyclerView: PokeListRecyclerViewAdapter
    private var _binding: FragmentItemListBinding? = null
    private val binding get() = _binding!!

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

    private fun onEvent(event: UIEvent) {
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
    private fun render(state: UIState) {
        Log.d(TAG, "render: $state")
        Log.d(TAG, "list: ${state.list}")
        adapterRecyclerView.items = state.list
        binding.swipeRefresh.isRefreshing = state.loading
        binding.progressBarHome.isVisible = state.loading
        binding.errorState.isVisible = state.error
        binding.emptyState.isVisible = state.empty
        adapterRecyclerView.notifyDataSetChanged()
    }

    /**
     * Configura el recycler junto con el adapter.
     */
    private fun setupRecycler() {
        adapterRecyclerView = PokeListRecyclerViewAdapter(listOf())
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
