package com.example.smartgreenscape.model

data class Tag(
    val id: Int,
    var name: String,
    var temperatureMin: Double,
    var temperatureMax: Double,
    var humidityMin: Double,
    var humidityMax: Double,
    var soilHumidityMin: Double,
    var soilHumidityMax: Double
)
