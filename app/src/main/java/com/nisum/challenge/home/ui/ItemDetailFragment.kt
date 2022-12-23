package com.nisum.challenge.home.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.databinding.FragmentItemDetailBinding
import com.nisum.challenge.home.ui.recyclerview.viewholder.PokeViewHolder.Companion.BASE_URL_IMAGE
import com.nisum.challenge.home.ui.recyclerview.viewholder.PokeViewHolder.Companion.FORMAT_PNG
import com.squareup.picasso.Picasso

/**
 * Representa el detalle del item seleccionado, obtiene la información a través de los argumentos.
 */
class ItemDetailFragment : Fragment() {

    private var item: PokeModel? = null

    private var _binding: FragmentItemDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_PARCELABLE)) {
                item = it.getParcelable(ARG_ITEM_PARCELABLE)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root
        updateContent()
        return rootView
    }

    private fun updateContent() {
        item?.let {
            binding.toolbarLayout.title = it.name
            Picasso
                .get()
                .load("${BASE_URL_IMAGE}${it.url?.toUri()?.lastPathSegment}.${FORMAT_PNG}")
                .into(binding.imageBackground)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ARG_ITEM_PARCELABLE = "item_repo_model_parcelable"
    }
}
