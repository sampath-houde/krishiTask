package com.task.krishinetwork.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.krishinetwork.api.repo.CropRepo
import com.task.krishinetwork.ui.viewmodel.OnlineCropViewModel
import java.lang.IllegalArgumentException

@Suppress("UNCHECKED_CAST")
class ApiViewModelFactory(
    private val repository: BaseRepository
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(OnlineCropViewModel::class.java) -> OnlineCropViewModel(repository as CropRepo) as T
            else -> throw IllegalArgumentException("ViewModel class not found")
        }
    }

}