package com.task.krishinetwork.ui.viewmodel

import android.app.Application
import android.graphics.Bitmap
import androidx.lifecycle.*
import com.task.krishinetwork.database.dao.UserDao
import com.task.krishinetwork.database.entities.User
import kotlinx.coroutines.launch

class UserViewModel(private val database: UserDao, application: Application) : AndroidViewModel(application) {

    var readAllUser: LiveData<List<User>> = database.readAllUsers()

    fun addNewUser(name: String, email: String, image: Bitmap?) = viewModelScope.launch {
        val user = User(0, name, email, image)
        database.addUsers(user)
    }

}