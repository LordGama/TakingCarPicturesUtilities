package com.lordgama.takingcarpicturesutilities

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.*

@Dao
interface MPhotoDao {

    @Query("SELECT * FROM photos WHERE vehicle = :vehicle AND type = :type")
    fun getPhoto(vehicle:Int, type :Int): List<MPhoto>

    @Query("SELECT * FROM photos WHERE vehicle = :vehicle AND type = :type")
    fun getPhoto2(vehicle:Int, type :Int): LiveData<List<MPhoto>>

    @Query("SELECT * FROM photos WHERE vehicle = :vehicle")
    fun getPhotos(vehicle:Int): List<MPhoto>

    @Query("SELECT * FROM photos WHERE vehicle = :vehicle AND type = :type")
    fun getPhotoObservable(vehicle:Int, type :Int): LiveData<List<MPhoto>>

    @Query("SELECT * FROM photos WHERE vehicle = :vehicle")
    fun getPhotosObservable(vehicle:Int): LiveData<List<MPhoto>>

    @Update
    fun update(vararg photos: MPhoto)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(photo: MPhoto): Long

}
