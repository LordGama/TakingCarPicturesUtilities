package com.lordgama.takingcarpicturesutilities

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query

@Dao
interface CustomVehicleDao {
    @Query("Select * from vehicles")
    fun getAll(): LiveData<List<MVehicle>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(vararg vehicle: MVehicle)
}