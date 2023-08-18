package com.ctyeung.colorbration.viewmodels

import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctyeung.colorbration.data.AttenuatorEvent
import com.ctyeung.colorbration.data.AttenuatorRepository
import com.ctyeung.colorbration.data.SpectralAttenuator
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReflectanceViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val attenuatorRepository: AttenuatorRepository
) : ViewModel() {
    // move to repository

    private val _event = MutableLiveData<SpectralEvent>()
    val event: LiveData<SpectralEvent> = _event

    init {
        initAttenuatorEventListener()
    }

    private fun initAttenuatorEventListener() {
        kotlin.runCatching {
            viewModelScope.launch {
                attenuatorRepository.event.collect() {
                    when (it) {
                        is AttenuatorEvent.Success -> {
                            _event.value = SpectralEvent.Success(it.sRGB, it.curve)
                        }

                        is AttenuatorEvent.Error -> {
                            _event.value = SpectralEvent.Error(it.msg)
                        }

                        else -> {
                            _event.value = SpectralEvent.Error("unknown")
                        }
                    }
                }
            }
        }.onFailure {
            Log.e("ReflectanceViewModel", it.toString())
        }
    }

    fun updateBy(index: Int, value: Double) {
        viewModelScope.launch {
            attenuatorRepository.updateBy(index, value * 100)
        }
    }
}

sealed class SpectralEvent() {
    object InProgress : SpectralEvent()
    class Success(val sRGB:Color, val curve: SpectralAttenuator) : SpectralEvent()
    class Error(val msg: String) : SpectralEvent()
}