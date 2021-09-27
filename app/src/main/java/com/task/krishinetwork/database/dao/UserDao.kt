package com.task.krishinetwork.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.Dao
import com.task.krishinetwork.database.entities.User
import com.task.krishinetwork.utils.KrishDao

@Dao
interface UserDao : KrishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUsers(user: User)

    @Query("SELECT * FROM user_table ORDER BY id DESC")
    fun readAllUsers(): LiveData<List<User>>

}