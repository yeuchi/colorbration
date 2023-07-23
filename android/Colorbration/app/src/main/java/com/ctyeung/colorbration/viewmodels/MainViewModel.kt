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
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {
    private val _event = MutableLiveData<ObserverEvent>()
    val event: LiveData<ObserverEvent> = _event

    init {
        /*
         * TODO retrieve and calculate chromaticity value
         */
    }
}

sealed class ObserverEvent() {
    object InProgress : ObserverEvent()
    class Success(val points: List<PointF>) : ObserverEvent()
    class Error(val msg: String) : ObserverEvent()
}