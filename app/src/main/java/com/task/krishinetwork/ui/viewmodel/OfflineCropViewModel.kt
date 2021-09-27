package com.task.krishinetwork.ui.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.task.krishinetwork.database.dao.CropDao
import com.task.krishinetwork.database.entities.Crop
import kotlinx.coroutines.launch

class OfflineCropViewModel(private val database: CropDao, application: Application) : AndroidViewModel(application) {

    var readAllCrops: LiveData<Crop> = database.readAllCrops()

    fun addCropData(crop: Crop) = viewModelScope.launch {
        database.addCropData(crop)
    }

    fun updateCropData(crop: Crop) = viewModelScope.launch {
        database.updateCropData(crop)
    }

}