package com.nisum.challenge.app.ui.view

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.nisum.challenge.app.ui.recyclerview.evolution.EvolutionRecyclerViewAdapter
import com.nisum.challenge.app.ui.recyclerview.list.PokeViewHolder.Companion.BASE_URL_IMAGE
import com.nisum.challenge.app.ui.recyclerview.list.PokeViewHolder.Companion.FORMAT_PNG
import com.nisum.challenge.app.ui.recyclerview.parks.PalParkEncounterRecyclerViewAdapter
import com.nisum.challenge.app.ui.recyclerview.stats.StatRecyclerViewAdapter
import com.nisum.challenge.app.ui.recyclerview.types.TypesRecyclerViewAdapter
import com.nisum.challenge.app.ui.viewmodel.PokeInfoViewModel
import com.nisum.challenge.common.Message
import com.nisum.challenge.common.UIEvent
import com.nisum.challenge.common.UIState
import com.nisum.challenge.common.getLasPath
import com.nisum.challenge.common.models.Evolution
import com.nisum.challenge.common.models.PokeInfo
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.common.models.SpeciesName
import com.nisum.challenge.common.models.getHeightString
import com.nisum.challenge.common.models.getWeightString
import com.nisum.challenge.databinding.FragmentItemDetailBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.flow.consumeAsFlow
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * Representa el detalle del item seleccionado, obtiene la información a través de los argumentos.
 */
class DetailFragment : Fragment(), IView<UIState<PokeInfo>, UIEvent> {

    private val viewModel by viewModel<PokeInfoViewModel>()
    private val typeAdapter = TypesRecyclerViewAdapter()
    private val statAdapter = StatRecyclerViewAdapter()
    private val palParkEncounterAdapter = PalParkEncounterRecyclerViewAdapter()
    private val evolutionAdapter = EvolutionRecyclerViewAdapter()

    private var item: PokeModel? = null
    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        return binding.root
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
        binding.evolutionRecyclerView.adapter = evolutionAdapter
    }

    /**
     * Actualiza el contenido que trae de la pantalla anterior.
     */
    private fun updateContent() {
        item?.let {
            binding.toolbarLayout.title = it.name.replaceFirstChar { char -> char.uppercase() }
            Picasso
                .get()
                .load("${BASE_URL_IMAGE}${it.url.getLasPath()}.${FORMAT_PNG}")
                .into(binding.imageBackground)
        }
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
                    it?.let { event ->
                        onEvent(event)
                    }
                }
        }
    }

    /**
     * Configura la visibilidad y muestra los datos en la vista.
     */
    override fun render(state: UIState<PokeInfo>) {
        Log.d(ContentValues.TAG, "render detail: $state")
        Log.d(ContentValues.TAG, "data detail: ${state.data}")
        updateAdapters(state)
        visibilitySuccess(state.data != null)
        binding.progressbar.isVisible = state.loading
        binding.errorState.isVisible = state.error
        state.data?.let { updateView(it) }
    }

    /**
     * Actualiza los items que necesita cada adapter.
     *
     * @param state UIState<PokeInfo>
     */
    private fun updateAdapters(state: UIState<PokeInfo>) {
        statAdapter.updateItems(state.data?.stats ?: listOf())
        palParkEncounterAdapter.updateItems(state.data?.species?.palParkEncounters ?: listOf())
        typeAdapter.updateItems(state.data?.types ?: listOf())
        renderEvolution(state.data?.evolution)
    }

    /**
     * Actualiza algunos valores en la vista
     *
     * @param pokeInfo PokeInfo
     */
    private fun updateView(pokeInfo: PokeInfo) {
        binding.name.text = pokeInfo.name.replaceFirstChar { it.uppercase() }
        binding.weight.text = pokeInfo.getWeightString()
        binding.height.text = pokeInfo.getHeightString()
    }

    /**
     * Configura la visibilidad del caso de exito.
     *
     * @param isVisible Boolean
     */
    private fun visibilitySuccess(isVisible: Boolean) {
        binding.findMeTitle.isVisible = isVisible
        binding.weightTitle.isVisible = isVisible
        binding.heightTitle.isVisible = isVisible
        binding.statsTitle.isVisible = isVisible
        binding.evolutionTitle.isVisible = isVisible
    }

    /**
     * Manejo de eventos de error y desiciones que toma la vista como por ejemplo mostrar el error y el mensaje.
     *
     * @param event UIEvent
     */
    override fun onEvent(event: UIEvent) {
        Log.d(ContentValues.TAG, "event: $event")
        when (event) {
            is Message -> {
                binding.errorState.isVisible = true
                binding.errorText.text = event.message
                binding.progressbar.isVisible = false
                visibilitySuccess(false)
            }
        }
    }

    /**
     * Configura el botón de retry y refresh.
     */
    private fun setupButtons() {
        binding.retryButtonError.setOnClickListener { viewModel.refresh() }
        binding.back.setOnClickListener { activity?.onBackPressed() }
    }

    private fun renderEvolution(evolution: Evolution?) {
        val species = mutableListOf<SpeciesName>()
        evolution?.chain?.species?.let { species.add(it) }
        evolution?.chain?.evolvesTo?.map { chain ->
            chain.species?.let { species.add(it) }
            chain.evolvesTo.map { chainTwo ->
                chainTwo.species?.let { species.add(it) }
            }
        }
        evolutionAdapter.updateItems(species)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_ITEM_PARCELABLE = "item_model_parcelable"
    }
}
