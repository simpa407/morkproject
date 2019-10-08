package com.example.tokyoartbeat

import java.time.LocalDate

data class EventDetails(
    var nameEvent: String,
    var nameVenue: String,
    var startDate: LocalDate,
    var endDate: LocalDate,
    var going_count: String,
    var descriptpion: String,
    var category: String,
    var address: String,
    var artist: String
)
