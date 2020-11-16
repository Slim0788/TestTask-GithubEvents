package com.testtask.events.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.testtask.events.data.Event
import com.testtask.events.network.ResultWrapper
import com.testtask.events.repository.EventsMapper
import com.testtask.events.repository.EventsRepository
import com.testtask.events.utils.SingleEventLiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EventsViewModel(
    private val eventsRepository: EventsRepository,
    private val eventsMapper: EventsMapper
) : ViewModel() {

    private val eventsListLiveData = MutableLiveData<List<Event>>()
    private val isProgressLiveData = MutableLiveData<Boolean>()
    private val errorMutableLiveData = SingleEventLiveData<String>()

    val eventsList: LiveData<List<Event>> = eventsListLiveData
    val isProgress: LiveData<Boolean> = isProgressLiveData
    val error: LiveData<String> = errorMutableLiveData

    fun getEvents() {
        getData()
    }

    fun updateEvents() {
        deleteAllDataFromDatabase()
        getData()
    }

    private fun getData() {
        isProgressLiveData.value = true
        viewModelScope.launch {
            val events = withContext(Dispatchers.IO) {
                eventsRepository.getData()
            }
            when (events) {
                is ResultWrapper.Success ->
                    eventsListLiveData.value = eventsMapper.map(events.value)
                is ResultWrapper.NetworkError -> {
                    errorMutableLiveData.value = "Network error"
                }
                is ResultWrapper.GenericError -> {
                    errorMutableLiveData.value = "code: ${events.code}, error: ${events.error}"
                }
            }
            isProgressLiveData.value = false
        }
    }

    private fun deleteAllDataFromDatabase() {
        viewModelScope.launch {
            withContext(Dispatchers.Default) {
                eventsRepository.deleteCachedData()
            }
        }
    }

}