package com.task.krishinetwork.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.task.krishinetwork.api.model.CropResponse
import com.task.krishinetwork.api.repo.CropRepo
import com.task.krishinetwork.utils.ApiResponseHandler
import kotlinx.coroutines.launch

class OnlineCropViewModel(private val cropRepo: CropRepo) : ViewModel() {

    private val _cropList: MutableLiveData<ApiResponseHandler<CropResponse>> = MutableLiveData()

    val cropList: LiveData<ApiResponseHandler<CropResponse>>
        get() = _cropList

    fun getCropDataFromApi() = viewModelScope.launch {
        _cropList.value = cropRepo.getCrop()
    }

}