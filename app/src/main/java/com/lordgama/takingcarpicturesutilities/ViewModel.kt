package com.lordgama.takingcarpicturesutilities

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Room

class ViewModel(application: Application) : AndroidViewModel(application){

    var database = Room.databaseBuilder(
            application.applicationContext,Database::class.java,
            "demo_database")
            .fallbackToDestructiveMigration()
            .build()

    fun getVehicles(): LiveData<List<CustomVehicle>>{
        return database.customVehicleDao().getAll()
    }

}