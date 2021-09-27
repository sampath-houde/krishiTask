package com.task.krishinetwork.database.repo

import androidx.lifecycle.LiveData
import com.task.krishinetwork.database.dao.UserDao
import com.task.krishinetwork.database.entities.User

class UserRepo(private val userDao: UserDao) {

    val readAllUser: LiveData<List<User>>
        get() = userDao.readAllUsers()


    suspend fun addNewUser(user: User) {
        userDao.addUsers(user)
    }
}