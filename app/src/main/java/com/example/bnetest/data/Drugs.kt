package com.example.bnetest.data

import java.io.Serializable

data class Drugs(
    val categories: Categories?,
    val description: String?,
    val documentation: String?,
    val fields: List<Field?>?,
    var id: Int,
    val image: String,
    val name: String?
) : Serializable

