package com.lordgama.takingcarpicturesutilities

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.persistence.room.Room

class ViewModel(application: Application) : AndroidViewModel(application){

    var vehicleToBeCaptured: MutableLiveData<MVehicle> = MutableLiveData()

    fun setVehicleToBeCaptured(vehicle: MVehicle){
        vehicleToBeCaptured.value = vehicle
    }

    var database = Room.databaseBuilder(
            application.applicationContext,Database::class.java,
            "demo_database")
            .fallbackToDestructiveMigration()
            .build()

    fun getVehicles(): LiveData<List<MVehicle>>{
        //loadVehicles()
        return database.customVehicleDao().getAll()
    }

    fun loadVehicles(){
        TODO("Not Implemented")
    }

}