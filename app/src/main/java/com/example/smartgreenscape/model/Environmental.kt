package com.example.smartgreenscape.model

data class Environmental(
    val __extras: Extras,
    val _links: Links,
    val fields: List<Field>,
    val include_total: Boolean,
    val limit: String,
    val offset: String,
    val records: List<Record>,
    val resource_format: String,
    val resource_id: String,
    val total: String
){
    data class Extras(
        val api_key: String
    )
    data class Field(
        val id: String,
        val info: Info,
        val type: String
    )
    data class Info(
        val label: String
    )
    data class Links(
        val next: String,
        val start: String
    )
    data class Record(
        val concentration: String,
        val county: String,
        val itemengname: String,
        val itemid: String,
        val itemname: String,
        val itemunit: String,
        val monitordate: String,
        val siteid: String,
        val sitename: String
    )
}