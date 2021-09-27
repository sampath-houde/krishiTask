package com.task.krishinetwork.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.task.krishinetwork.database.entities.Crop
import com.task.krishinetwork.utils.KrishDao

@Dao
interface CropDao : KrishDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addCropData(crop: Crop)

    @Update
    suspend fun updateCropData(crop: Crop)

    @Query("SELECT * FROM crop_table ORDER BY sr_no DESC")
    fun readAllCrops(): LiveData<Crop>

}