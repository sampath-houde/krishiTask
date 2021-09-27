package com.task.krishinetwork.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.task.krishinetwork.api.model.OtherMandi
import com.task.krishinetwork.utils.OtherMandiConvertor


@Entity(tableName = "crop_table")
data class Crop(
    @PrimaryKey(autoGenerate = false)
    var sr_no: Int,
    val other_mandi: List<OtherMandi>
) {
  init {
      sr_no = 0
  }
}
