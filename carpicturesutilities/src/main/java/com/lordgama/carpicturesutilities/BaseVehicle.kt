package com.lordgama.carpicturesutilities

/**
 * Clase base de vehiculo usada para extender atributos base
 */

class BaseVehicle(
        var id: Int = 0,
        var customAgentName: String = "",
        var latitude: Double = 0.000000,
        var longitude: Double = 0.000000,
        var make: String = "",
        var model: String = "",
        var pediment: String = "",
        var pedimentDate: String = "",
        var status: Status = Status.PENDING_STATUS,
        var vin: String = "",
        var year: String = ""
)