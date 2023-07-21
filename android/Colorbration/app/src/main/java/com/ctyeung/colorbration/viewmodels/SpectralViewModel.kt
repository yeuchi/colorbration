package com.ctyeung.colorbration.viewmodels

import android.content.Context
import android.graphics.PointF
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class SpectralViewModel @Inject constructor(
    @ApplicationContext context: Context
): ViewModel() {

    var touchPoints = ArrayList<PointF>()

    private val _event = MutableLiveData<SpectralEvent>()
    val event: LiveData<SpectralEvent> = _event

    init {

    }

    fun clear() {
        touchPoints.clear()
    }

    fun add(p:PointF) = touchPoints.add(p)

    fun invalidate() {
        val points = touchPoints.toList()
        _event.value = SpectralEvent.Success(points)
    }
}

sealed class SpectralEvent() {
    object InProgress : SpectralEvent()
    class Success(val points:List<PointF>):SpectralEvent()
    class Error(val msg:String):SpectralEvent()
}