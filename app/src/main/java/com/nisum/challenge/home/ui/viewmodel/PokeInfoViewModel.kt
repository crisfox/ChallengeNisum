package com.nisum.challenge.home.ui.viewmodel

import android.content.res.Resources
import androidx.core.net.toUri
import androidx.lifecycle.viewModelScope
import com.nisum.challenge.R
import com.nisum.challenge.common.Message
import com.nisum.challenge.common.UIEvent
import com.nisum.challenge.common.UIState
import com.nisum.challenge.common.getLasPath
import com.nisum.challenge.common.models.PokeInfo
import com.nisum.challenge.common.models.PokeModel
import com.nisum.challenge.common.networking.Loading
import com.nisum.challenge.common.networking.Success
import com.nisum.challenge.home.repositories.PokeInfoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

/**
 * Mantiene los datos persistentes en el viewModelScope
 */
internal class PokeInfoViewModel(
    private val repository: PokeInfoRepository,
    private val resource: Resources
) : BaseViewModel<UIState<PokeInfo>, UIEvent>() {

    override val initialState
        get() = UIState<PokeInfo>(loading = true)

    private var loading = false
    private var info: PokeInfo? = null
    private var error = false
    private var pokeModel: PokeModel? = null

    fun refresh() {
        fetchInfoPoke(pokeModel)
    }

    fun fetchInfoPoke(pokeModel: PokeModel?) {
        pokeModel?.let {
            this.pokeModel = it
            viewModelScope.launch(Dispatchers.IO) {
                repository
                    .getInfo(it.url.getLasPath() ?: "", it.name)
                    .collectLatest { result ->
                        when (result) {
                            is Success -> {
                                loading = false
                                error = false
                                info = result.data
                            }
                            is Loading -> {
                                loading = true
                                error = false
                                info = result.data
                            }
                            else -> {
                                loading = false
                                error = true
                                pushEvent(Message(result.message))
                            }
                        }
                        invalidate()
                    }
            }
        } ?: run {
            loading = false
            error = true
            pushEvent(Message(resource.getString(R.string.err_default_msg)))
        }
    }

    private fun invalidate() {
        updateState(
            UIState(
                loading = loading,
                data = info,
                error = error
            )
        )
    }
}
