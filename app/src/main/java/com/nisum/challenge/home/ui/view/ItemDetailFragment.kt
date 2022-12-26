package com.nisum.challenge.home.ui.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nisum.challenge.common.Message
import com.nisum.challenge.common.UIEvent
import com.nisum.challenge.common.UIState
import com.nisum.challenge.common.getLasPath
import com.nisum.challenge.common.models.PokeInfo
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.common.models.getHeightString
import com.nisum.challenge.common.models.getWeightString
import com.nisum.challenge.databinding.FragmentItemDetailBinding
import com.nisum.challenge.home.ui.recyclerview.adapter.list.PokeViewHolder.Companion.BASE_URL_IMAGE
import com.nisum.challenge.home.ui.recyclerview.adapter.list.PokeViewHolder.Companion.FORMAT_PNG
import com.nisum.challenge.home.ui.recyclerview.adapter.parks.PalParkEncounterRecyclerViewAdapter
import com.nisum.challenge.home.ui.recyclerview.adapter.stats.StatRecyclerViewAdapter
import com.nisum.challenge.home.ui.recyclerview.adapter.types.TypesRecyclerViewAdapter
import com.nisum.challenge.home.ui.viewmodel.PokeInfoViewModel
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Representa el detalle del item seleccionado, obtiene la información a través de los argumentos.
 */
class ItemDetailFragment : Fragment(), IView<UIState<PokeInfo>, UIEvent> {

    private val viewModel by viewModel<PokeInfoViewModel>()
    private val typeAdapter = TypesRecyclerViewAdapter()
    private val statAdapter = StatRecyclerViewAdapter()
    private val palParkEncounterAdapter = PalParkEncounterRecyclerViewAdapter()

    private var item: PokeModel? = null
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            if (it.containsKey(ARG_ITEM_PARCELABLE)) {
                item = it.getParcelable(ARG_ITEM_PARCELABLE)
            }
        }
        setupRecycler()
        setupButtons()
        updateContent()
        subscribe()
        viewModel.fetchInfoPoke(item)
    }

    /**
     * Configura el recycler junto con el adapter.
     */
    private fun setupRecycler() {
        binding.typesRecyclerView.adapter = typeAdapter
        binding.parksRecyclerView.adapter = palParkEncounterAdapter
        binding.statsRecyclerView.adapter = statAdapter
    }

    private fun updateContent() {
        item?.let {
            binding.toolbarLayout.title = it.name.replaceFirstChar { char -> char.uppercase() }
            Picasso
                .get()
                .load("${BASE_URL_IMAGE}${it.url.getLasPath()}.${FORMAT_PNG}")
                .into(binding.imageBackground)
        }
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

    override fun render(state: UIState<PokeInfo>) {
        Log.d(ContentValues.TAG, "render detail: $state")
        Log.d(ContentValues.TAG, "data detail: ${state.data}")
        statAdapter.updateItems(state.data?.stats ?: listOf())
        palParkEncounterAdapter.updateItems(state.data?.species?.palParkEncounters ?: listOf())
        typeAdapter.updateItems(state.data?.types ?: listOf())
        binding.progressbar.isVisible = state.loading
        binding.errorState.isVisible = state.error
        state.data?.let { updateView(it) }
    }

    private fun updateView(pokeInfo: PokeInfo) {
        binding.name.text = pokeInfo.name.replaceFirstChar { it.uppercase() }
        binding.weight.text = pokeInfo.getWeightString()
        binding.height.text = pokeInfo.getHeightString()
    }

    override fun onEvent(event: UIEvent) {
        Log.d(ContentValues.TAG, "event: $event")
        when (event) {
            is Message -> {
                binding.errorState.isVisible = true
                binding.errorText.text = event.message
            }
        }
    }

    /**
     * Configura el botón de retry y refresh.
     */
    private fun setupButtons() {
        binding.retryButtonError.setOnClickListener { viewModel.refresh() }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_ITEM_PARCELABLE = "item_repo_model_parcelable"
    }
}
