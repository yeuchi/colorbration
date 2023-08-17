package com.ctyeung.colorbration.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctyeung.colorbration.data.SourceRepository
import com.ctyeung.colorbration.data.BaseSpectralData
import com.ctyeung.colorbration.data.ObserverDataEvent
import com.ctyeung.colorbration.data.SourceDataEvent
import com.ctyeung.colorbration.data.SpectralReflectance
import com.ctyeung.colorbration.data.ref.LightSources
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val sourceRepository: SourceRepository
) : ViewModel() {

    private val _event = MutableLiveData<SourceEvent>()
    val event: LiveData<SourceEvent> = _event

    private var _selectedIlluminant: String = LightSources.ILLUMINANT_A
    val selectedIlluminant: String
        get() {
            return _selectedIlluminant
        }

    init {
        initSourceDataEventListener()
    }

    private fun initSourceDataEventListener() {
        kotlin.runCatching {
            viewModelScope.launch {
                sourceRepository.event.collect() {
                    when(it) {
                        is SourceDataEvent.Success -> {
                            _selectedIlluminant = it.selectedSource
                            loadSourceCurve()
                        }

                        is SourceDataEvent.Error -> {
                            _event.value = SourceEvent.Error(it.msg)
                        }
                    }
                }
            }
        }.onFailure {
            Log.e("IlluminantViewModel", it.toString())
        }
    }

    private fun loadSourceCurve() {
        val list = LightSources.retrieve(_selectedIlluminant)
        _event.value = SourceEvent.Success(SpectralReflectance(list))
    }

    fun updateBy(selectedSource: String) {
        viewModelScope.launch {
            sourceRepository.updateBy(selectedSource)
        }
    }
}

sealed class SourceEvent() {
    object InProgress : SourceEvent()
    class Success(val data: SpectralReflectance) : SourceEvent()
    class Error(val msg: String) : SourceEvent()
}