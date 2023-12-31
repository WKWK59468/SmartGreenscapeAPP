package com.example.smartgreenscape.model

data class Plant(
    var temperatureMin: Double = 0.0,
    var temperatureMax: Double = 0.0,
    var humidityMin: Double = 0.0,
    var humidityMax: Double = 0.0,
    var soilHumidityMin: Double = 0.0,
    var soilHumidityMax: Double = 0.0
)
