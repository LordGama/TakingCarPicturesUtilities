package com.lordgama.takingcarpicturesutilities

import android.arch.persistence.room.TypeConverter
import com.lordgama.carpicturesutilities.VehiclePhoto

class PhotoTypeConverter(){
    @TypeConverter
    fun toPhotoType(type: Int) = VehiclePhoto.PhotoType.toPhotoType(type)

    @TypeConverter
    fun fromPhoto(photoType: VehiclePhoto.PhotoType): Int {
        return VehiclePhoto.PhotoType.fromPhoto(photoType)
    }
}