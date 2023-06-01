package com.nisum.challenge.ui.viewmodel

import android.content.res.Resources
import androidx.lifecycle.viewModelScope
import com.nisum.challenge.R
import com.nisum.challenge.domain.model.PokeInfo
import com.nisum.challenge.domain.model.PokeModel
import com.nisum.challenge.data.network.model.Loading
import com.nisum.challenge.data.network.model.Success
import com.nisum.challenge.domain.GetInfoPokeUseCase
import com.nisum.challenge.ui.view.common.Message
import com.nisum.challenge.ui.view.common.UIEvent
import com.nisum.challenge.ui.view.common.UIState
import com.nisum.challenge.ui.view.common.getLastPath
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Mantiene los datos persistentes en el viewModelScope y envia eventos para que la view pueda tomar desiciones.
 */
internal class PokeInfoViewModel(
    private val getInfoPokeUseCase: GetInfoPokeUseCase,
    private val resource: Resources,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BaseViewModel<UIState<PokeInfo>, UIEvent>() {

    override val initialState
        get() = UIState<PokeInfo>(loading = true)

    private var pokeModel: PokeModel? = null

    fun refresh() {
        fetchInfoPoke(pokeModel)
    }

    fun fetchInfoPoke(pokeModel: PokeModel?) {
        pokeModel?.let {
            this.pokeModel = it
            viewModelScope.launch(dispatcher) {
                getInfoPokeUseCase
                    .invoke(it.url.getLastPath() ?: "", it.name)
                    .collectLatest { result ->
                        when (result) {
                            is Success -> {
                                update(info = result.data)
                            }
                            is Loading -> {
                                update(
                                    loading = true,
                                    info = result.data
                                )
                            }
                            else -> {
                                update(error = true)
                                pushEvent(Message(result.message))
                            }
                        }
                    }
            }
        } ?: run {
            update(error = true)
            pushEvent(Message(resource.getString(R.string.err_default_msg)))
        }
    }

    private fun update(loading: Boolean = false, info: PokeInfo? = null, error: Boolean = false) {
        updateState(
            UIState(
                loading = loading,
                data = info,
                error = error
            )
        )
    }
}
