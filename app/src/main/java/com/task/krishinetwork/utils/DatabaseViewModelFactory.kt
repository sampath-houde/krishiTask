package com.task.krishinetwork.utils

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.task.krishinetwork.database.dao.CropDao
import com.task.krishinetwork.database.dao.UserDao
import com.task.krishinetwork.ui.viewmodel.OfflineCropViewModel
import com.task.krishinetwork.ui.viewmodel.UserViewModel

class DatabaseViewModelFactory(
    private val dataSource: KrishDao,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {

        when(dataSource) {
            is UserDao -> {
                if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                    return UserViewModel(dataSource, application) as T
                }
            }

            is CropDao -> {
                if (modelClass.isAssignableFrom(OfflineCropViewModel::class.java)) {
                    return OfflineCropViewModel(dataSource, application) as T
                }
            }
        }


        throw IllegalArgumentException("Unknown ViewModel class")
    }
}