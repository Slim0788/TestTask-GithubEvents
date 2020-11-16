package com.testtask.events.repository

import com.testtask.events.api.dto.ResponseDto
import com.testtask.events.data.Event

class EventsMapper {
    fun map(responseList: List<ResponseDto>): List<Event> {
        return responseList.map { movieDto -> map(movieDto) }
    }

    private fun map(responseDto: ResponseDto) =
        Event(
            id = responseDto.id,
            login = responseDto.actor.login,
            avatarUrl = responseDto.actor.avatarUrl,
            event = responseDto.event,
            date = responseDto.date,
            redirectUrl = responseDto.repo.redirectUrl
        )
}