package com.testtask.events.api.dto

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class ResponseDto(
    @PrimaryKey
    @Json(name = "id")
    val id: String,
    @Json(name = "type")
    val event: String,
    @Json(name = "actor")
    @Embedded
    val actor: ActorDto,
    @Json(name = "repo")
    @Embedded
    val repo: RepoDto,
    @Json(name = "created_at")
    val date: String
)