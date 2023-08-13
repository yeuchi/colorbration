package com.ctyeung.colorbration.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ctyeung.colorbration.data.ObserverDataEvent
import com.ctyeung.colorbration.data.ObserverRepository
import com.ctyeung.colorbration.data.SpectralObserver
import com.ctyeung.colorbration.data.ref.StandardObserver
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext context: Context,
    private val observerRepository: ObserverRepository
) : ViewModel() {

    private var _selectedObserver: String = StandardObserver.FUNC_10D_1964
    var selectedObserver: String = _selectedObserver
        get() = _selectedObserver

    private val _event = MutableLiveData<ObserverEvent>()
    val event: LiveData<ObserverEvent> = _event

    init {
        initObserverEventListener()
    }

    private fun initObserverEventListener() {
        kotlin.runCatching {
            viewModelScope.launch {
                observerRepository.event.collect() {
                    when(it) {
                        is ObserverDataEvent.Success -> {
                            _selectedObserver = it.selectedObserver
                            loadStandardObserverCurve()
                        }

                        is ObserverDataEvent.Error -> {
                            _event.value = ObserverEvent.Error(it.msg)
                        }
                    }
                }
            }
        }.onFailure {
            Log.e("WeatherViewModel.initUnitListener", it.toString())
        }
    }

    private fun loadStandardObserverCurve() {
        when(_selectedObserver) {
            StandardObserver.FUNC_2D_1931 -> select2degrees()
            StandardObserver.FUNC_10D_1964 -> select10degrees()
        }
    }

    fun select10degrees() {
        _selectedObserver = StandardObserver.FUNC_10D_1964
        _event.value = ObserverEvent.Success(observerRepository.standardObserver2Degree)
    }

    fun select2degrees() {
        _selectedObserver = StandardObserver.FUNC_2D_1931
        _event.value = ObserverEvent.Success(observerRepository.standardObserver10Degree)
    }

    fun updateBy(selectedObserver: String) {
        viewModelScope.launch {
            observerRepository.updateBy(selectedObserver)
        }
    }
}

sealed class ObserverEvent() {
    object InProgress : ObserverEvent()
    class Success(val data: List<SpectralObserver>) : ObserverEvent()
    class Error(val msg: String) : ObserverEvent()
}