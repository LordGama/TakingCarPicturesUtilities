package com.lordgama.carpicturesutilities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey

/**
 * Clase base de vehiculo usada para extender atributos base
 */

abstract class BaseVehicle{
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    open var id: Int = 0
    open var customAgentName: String = ""
    open var latitude: Double = 0.000000
    open var longitude: Double = 0.000000
    open var make: String = ""
    open var model: String = ""
    open var pediment: String = ""
    @ColumnInfo(name = "pediment_date")
    open var pedimentDate: String = ""
    open var status: Status = Status.PENDING_STATUS
    open var vin: String = ""
    open var year: String = ""
}