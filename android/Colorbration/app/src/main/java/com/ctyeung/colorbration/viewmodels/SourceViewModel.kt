package com.ctyeung.colorbration.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctyeung.colorbration.data.SourceRepository
import com.ctyeung.colorbration.data.SpectralData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SourceViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val sourceRepository: SourceRepository
) : ViewModel() {

    companion object {
        const val ILLUMINANT_A = "illuminant A"
        const val ILLUMINANT_B = "illuminant B"
        const val ILLUMINANT_C = "illuminant C"
        const val ILLUMINANT_D50 = "illuminant D50"
        const val ILLUMINANT_D65 = "illuminant D65"
    }

    private val _event = MutableLiveData<SourceEvent>()
    val event: LiveData<SourceEvent> = _event

    /*
     * TODO replace with preference storage later
     */
    private var _selectedIlluminant: String = ILLUMINANT_A
    val selectedIlluminant: String
        get() {
            return _selectedIlluminant
        }

    init {
        selectIlluminantA()
    }

    fun selectIlluminantA() {
        _selectedIlluminant = ILLUMINANT_A
        _event.value = SourceEvent.Success(sourceRepository.IlluminantA)
    }

    fun selectIlluminantB() {
        _selectedIlluminant = ILLUMINANT_B
        _event.value = SourceEvent.Success(sourceRepository.IlluminantB)
    }

    fun selectIlluminantC() {
        _selectedIlluminant = ILLUMINANT_C
        _event.value = SourceEvent.Success(sourceRepository.IlluminantC)
    }

    fun selectIlluminantD50() {
        _selectedIlluminant = ILLUMINANT_D50
        _event.value = SourceEvent.Success(sourceRepository.IlluminantD50)
    }

    fun selectIlluminantD65() {
        _selectedIlluminant = ILLUMINANT_D65
        _event.value = SourceEvent.Success(sourceRepository.IlluminantD65)
    }
}

sealed class SourceEvent() {
    object InProgress : SourceEvent()
    class Success(val data: List<SpectralData>) : SourceEvent()
    class Error(val msg: String) : SourceEvent()
}