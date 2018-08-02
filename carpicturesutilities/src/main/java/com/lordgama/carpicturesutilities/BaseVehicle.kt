package com.lordgama.carpicturesutilities
/**
 * Clase base de vehiculo usada para extender atributos base
 */

abstract class BaseVehicle{
    abstract var id: Int
    abstract var customAgentName: String
    open var latitude: Double = 0.000000
    open var longitude: Double = 0.000000
    open var make: String = ""
    open var model: String = ""
    open var pediment: String = ""
    open var pedimentDate: String = ""
    open var status: Status = Status.PENDING_STATUS
    open var vin: String = ""
    open var year: String = ""
}