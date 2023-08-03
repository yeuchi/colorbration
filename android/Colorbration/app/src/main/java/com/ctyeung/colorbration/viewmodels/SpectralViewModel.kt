package com.ctyeung.colorbration.viewmodels

import android.content.Context
import android.graphics.PointF
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctyeung.colorbration.data.MyPoint
import com.ctyeung.colorbration.data.SpectralData
import com.ctyeung.colorbration.data.SpectralReflectance
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SpectralViewModel @Inject constructor(
    @ApplicationContext context: Context
): ViewModel() {
    // move to repository
    var curve = SpectralReflectance()

    private val _event = MutableLiveData<SpectralEvent>()
    val event: LiveData<SpectralEvent> = _event

    init {
        _event.value = SpectralEvent.Success(curve)
    }

    fun clear() {
        curve.clear()
    }

    fun add(p: MyPoint) = curve.add(p)

    fun invalidate() {
        _event.value = SpectralEvent.Success(curve)
    }
}

sealed class SpectralEvent() {
    object InProgress : SpectralEvent()
    class Success(val curve:SpectralReflectance):SpectralEvent()
    class Error(val msg:String):SpectralEvent()
}