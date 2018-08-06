package com.lordgama.takingcarpicturesutilities

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.lordgama.carpicturesutilities.VehiclePhoto


@Database(entities = arrayOf(
        CustomVehicle::class,
        MPhoto::class
),version = 2)

@TypeConverters(StatusConverter::class,PhotoTypeConverter::class)
abstract class Database(): RoomDatabase(){
    abstract fun customVehicleDao(): CustomVehicleDao
    abstract fun mPhotoDao(): MPhotoDao
}