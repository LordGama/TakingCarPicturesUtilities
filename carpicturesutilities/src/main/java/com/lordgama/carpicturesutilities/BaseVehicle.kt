package com.lordgama.carpicturesutilities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey

/**
 * Clase base de vehiculo usada para extender atributos base
 */

abstract class BaseVehicle(
        @PrimaryKey(autoGenerate = true)
        @ColumnInfo(name = "id")
        var id: Int = 0,
        var customAgentName: String = "",
        var latitude: Double = 0.000000,
        var longitude: Double = 0.000000,
        var make: String = "",
        var model: String = "",
        var pediment: String = "",
        @ColumnInfo(name = "pediment_date")
        var pedimentDate: String = "",
        var status: Status = Status.PENDING_STATUS,
        var vin: String = "",
        var year: String = ""
)