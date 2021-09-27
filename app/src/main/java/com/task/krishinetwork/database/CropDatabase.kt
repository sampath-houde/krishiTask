package com.task.krishinetwork.database

import android.content.Context
import androidx.room.*
import com.task.krishinetwork.database.dao.CropDao
import com.task.krishinetwork.database.entities.Crop
import com.task.krishinetwork.utils.OtherMandiConvertor

@TypeConverters(OtherMandiConvertor::class)
@Database(entities = [Crop::class], version = 1, exportSchema = false )
abstract class CropDatabase: RoomDatabase() {
    abstract fun cropDao(): CropDao

    companion object {
        @Volatile
        private var INSTANCE: CropDatabase? = null

        fun getDatabase(context: Context): CropDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    CropDatabase::class.java,
                    "crop_table"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}