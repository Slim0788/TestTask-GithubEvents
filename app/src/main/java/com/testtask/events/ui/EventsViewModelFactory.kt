package com.testtask.events.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.testtask.events.repository.EventsMapper
import com.testtask.events.repository.EventsRepository

class EventsViewModelFactory(
    private val eventsRepository: EventsRepository,
    private val eventsMapper: EventsMapper
) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass == EventsViewModel::class.java) {
            @Suppress("UNCHECKED_CAST")
            EventsViewModel(
                eventsRepository,
                eventsMapper
            ) as T
        } else {
            throw IllegalArgumentException()
        }
    }
}