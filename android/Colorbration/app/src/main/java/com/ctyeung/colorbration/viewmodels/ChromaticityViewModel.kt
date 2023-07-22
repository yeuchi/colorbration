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
class ChromaticityViewModel @Inject constructor(
    @ApplicationContext context: Context
): ViewModel() {
    private val _event = MutableLiveData<ChromaticEvent>()
    val event: LiveData<ChromaticEvent> = _event

    init {
        /*
         * TODO retrieve and calculate chromaticity value
         */
    }
}

sealed class ChromaticEvent() {
    object InProgress : ChromaticEvent()
    class Success(val points:List<PointF>):ChromaticEvent()
    class Error(val msg:String):ChromaticEvent()
}