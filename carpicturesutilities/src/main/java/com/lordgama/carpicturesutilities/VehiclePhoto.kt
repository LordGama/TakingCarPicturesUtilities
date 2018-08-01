package com.lordgama.carpicturesutilities

import android.arch.persistence.room.TypeConverter

/**
 * Created by Daniel on 26/07/2018.
 *
 * @Version 1
 */

abstract class VehiclePhoto(){
    abstract var id: Int
    abstract var photoUrlString: String
    abstract var vehicle: Int //Id del vehiculo al que pertenece
    abstract var type: PhotoType
    abstract var date: String
    abstract var latitude: Float
    abstract var longitude: Float

    //abstract class PhotoType(name: String, ordinal: Int): Enum<Int>(name, ordinal) {}

    enum class PhotoType(val type: Int){
        PREVIEW(0),
        VIN(1),
        FRONT(2),
        PASSENGER_SIDE(3),
        BACK(4),
        DRIVER_SIDE(5),
        DOOR(6);

        companion object {
            private val map = PhotoType.values().associateBy(PhotoType::type);

            @TypeConverter
            @JvmStatic
            fun toPhotoType(type: Int) = map[type]

            @TypeConverter
            @JvmStatic
            fun fromStatus(photoType: PhotoType): Int {
                return photoType.type
            }


        }
    }
}