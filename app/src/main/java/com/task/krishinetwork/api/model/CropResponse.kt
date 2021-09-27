package com.task.krishinetwork.api.model

import com.task.krishinetwork.database.entities.Crop

data class CropResponse(
    val code: Int,
    val data: Data,
    val status: String
)

data class Data(
    val other_mandi: List<OtherMandi>
)

data class OtherMandi(
    val crop_id: Int,
    val district: String,
    val hindi_name: String,
    val id: Int,
    val image: String,
    val km: Double,
    val market: String,
    val state: String,
)