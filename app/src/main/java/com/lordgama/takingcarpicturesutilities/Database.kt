package com.lordgama.takingcarpicturesutilities

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters


@Database(entities = arrayOf(
        MVehicle::class,
        MPhoto::class
),version = 3)

@TypeConverters(StatusConverter::class,PhotoTypeConverter::class)
abstract class Database(): RoomDatabase(){
    abstract fun customVehicleDao(): CustomVehicleDao
    abstract fun mPhotoDao(): MPhotoDao
}