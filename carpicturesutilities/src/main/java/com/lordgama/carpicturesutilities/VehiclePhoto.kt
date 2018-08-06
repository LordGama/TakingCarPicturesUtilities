package com.lordgama.carpicturesutilities
/**
 * Created by Daniel on 26/07/2018.
 *
 * @Version 1
 */
abstract class VehiclePhoto{
    abstract var id: Int
    open var photoUrlString: String = ""
    open var vehicle: Int = 0 //Id del vehiculo al que pertenece
    open var type: PhotoType = PhotoType.PREVIEW
    open var date: String = ""
    open var latitude: Float = 0.000000F
    open var longitude: Float = 0.000000F

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
            fun toPhotoType(type: Int) = map[type]

            fun fromPhoto(photoType: PhotoType): Int {
                return photoType.type
            }


        }
    }
}