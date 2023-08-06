package com.ctyeung.colorbration.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctyeung.colorbration.data.AttenuatorRepository
import com.ctyeung.colorbration.data.math.MyPoint
import com.ctyeung.colorbration.data.SpectralReflectance
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
        getDefault()
    }

    fun getDefault() {
        _event.value = SpectralEvent.Success(attenuatorRepository.default)

    }

    fun clear() {
    }

    fun add(p: MyPoint) {
        /*
         * TODO need to replace existing point if exist
         *  Or add new knot
         */
    }

    fun invalidate() {
    }
}

sealed class SpectralEvent() {
    object InProgress : SpectralEvent()
    class Success(val curve: SpectralReflectance) : SpectralEvent()
    class Error(val msg: String) : SpectralEvent()
}