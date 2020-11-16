package com.testtask.events.api.dto

import androidx.room.ColumnInfo
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RepoDto(
    @Json(name = "url")
    @ColumnInfo(name = "redirect_url")
    val redirectUrl: String
)