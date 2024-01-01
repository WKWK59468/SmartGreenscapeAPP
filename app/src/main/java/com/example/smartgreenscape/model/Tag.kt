package com.example.smartgreenscape.model

data class Tag(
    val id: Int,
    var name: String,
    var min_temperature: Double,
    var max_temperature: Double,
    var min_humidity: Double,
    var max_humidity: Double,
    var min_soil_humidity: Double,
    var max_soil_humidity: Double
)
