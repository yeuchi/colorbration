package com.ctyeung.colorbration.viewmodels

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ctyeung.colorbration.SpectralObserver
import com.ctyeung.colorbration.data.ObserverRepository
import com.ctyeung.colorbration.data.BaseSpectralData
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    protected val observerRepository: ObserverRepository
) : ViewModel() {
    companion object {
        const val OBSERVER_2_DEGREES = "2 degrees"
        const val OBSERVER_10_DEGREES = "10 degrees"
    }

    private var _selectedObserver: String = OBSERVER_10_DEGREES
    var selectedObserver: String = _selectedObserver
        get() = _selectedObserver

    private val _event = MutableLiveData<ObserverEvent>()
    val event: LiveData<ObserverEvent> = _event

    init {
        select10degrees()
    }

    fun select10degrees() {
        _selectedObserver = OBSERVER_10_DEGREES
        _event.value = ObserverEvent.Success(observerRepository.standardObserver2Degree)
    }

    fun select2degrees() {
        _selectedObserver = OBSERVER_2_DEGREES
        _event.value = ObserverEvent.Success(observerRepository.standardObserver10Degree)
    }
}

sealed class ObserverEvent() {
    object InProgress : ObserverEvent()
    class Success(val data: List<SpectralObserver>) : ObserverEvent()
    class Error(val msg: String) : ObserverEvent()
}