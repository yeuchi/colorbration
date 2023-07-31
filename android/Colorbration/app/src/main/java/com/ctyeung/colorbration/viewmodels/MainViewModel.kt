package com.ctyeung.colorbration.viewmodels

import android.content.Context
import android.graphics.PointF
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctyeung.colorbration.data.ObserverRepository
import com.ctyeung.colorbration.data.SpectralData
import com.ctyeung.colorbration.data.StandardObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    observerRepository: ObserverRepository
) : ViewModel() {
    private val _event = MutableLiveData<ObserverEvent>()
    val event: LiveData<ObserverEvent> = _event

    init {
        _event.value = ObserverEvent.Success(observerRepository.standardObserver10Degree)
    }
}

sealed class ObserverEvent() {
    object InProgress : ObserverEvent()
    class Success(val data: List<SpectralData>) : ObserverEvent()
    class Error(val msg: String) : ObserverEvent()
}