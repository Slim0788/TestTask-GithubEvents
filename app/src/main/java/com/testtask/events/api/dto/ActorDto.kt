package com.testtask.events.api.dto

import androidx.room.ColumnInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ActorDto(
    @Json(name = "login")
    val login: String,
    @Json(name = "avatar_url")
    @ColumnInfo(name = "avatar_url")
    val avatarUrl: String
)