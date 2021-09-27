package com.task.krishinetwork.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.task.krishinetwork.database.entities.User
import com.task.krishinetwork.database.dao.UserDao
import com.task.krishinetwork.utils.BitmapConvertor

@TypeConverters(BitmapConvertor::class)
@Database(entities = [User::class], version = 1, exportSchema = false )
abstract class UserDatabase: RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object {
        @Volatile
        private var INSTANCE: UserDatabase? = null

        fun getDatabase(context: Context): UserDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    UserDatabase::class.java,
                    "user_table"
                ).build()
                INSTANCE = instance
                return instance
            }

        }
    }
}