package com.example.smartgreenscape.model

data class Plant(
    var min_temperature: Double = 0.0,
    var max_temperature: Double = 0.0,
    var min_humidity: Double = 0.0,
    var max_humidity: Double = 0.0,
    var min_soil_humidity: Double = 0.0,
    var max_soil_humidity: Double = 0.0
)
