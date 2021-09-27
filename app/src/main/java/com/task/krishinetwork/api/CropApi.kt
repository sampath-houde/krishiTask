package com.task.krishinetwork.api

import com.task.krishinetwork.api.model.CropResponse
import retrofit2.http.GET

interface CropApi {

    @GET("mandi?lat=28.44108136&lon=77.0526054&ver=89&lang=hi&crop_id=10")
    suspend fun getCropData() : CropResponse

}