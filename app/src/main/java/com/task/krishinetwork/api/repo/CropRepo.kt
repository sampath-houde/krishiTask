package com.task.krishinetwork.api.repo

import com.task.krishinetwork.api.CropApi
import com.task.krishinetwork.utils.BaseRepository

class CropRepo(private val api: CropApi) : BaseRepository() {

    suspend fun getCrop() = safeApiCall {
        api.getCropData()
    }

}