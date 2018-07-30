package com.lordgama.carpicturesutilities

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment

abstract class VehicleResumeBaseFragment: Fragment(){

    /*
    Related to Page Navigation
     */
    var captureFlowListener: CaptureFlowListener? = null
    val ARGUMENT_FRAGMENT_POSITION_KEY = "ARGUMENT_FRAGMENT_POSITION"
    var ARGUMENT_FRAGMENT_POSITION: Int = 0

    /*
    Vin & Description
     */
    var ARGUMENT_DESCRIPTION_KEY = "ARGUMENT_DESCRIPTION"
    var ARGUMENT_DESCRIPTION = ""
    var ARGUMENT_VIN_KEY = "ARGUMENT_VIN"
    var ARGUMENT_VIN = ""

    /*
    list of photos to overwrite during capture
     */
    abstract var photosList: MutableList<VehiclePhoto>

    /*
    Vehicle POJO
     */
    abstract var vehicle: BaseVehicle

    /**
     * Instantiate the listener with the context of the fragment
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Activity) {
            try {
                captureFlowListener = context as CaptureFlowListener
            } catch (e: ClassCastException) {
                throw ClassCastException(activity.toString() + " must implement NavigateBetweenPagesListener")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ARGUMENT_FRAGMENT_POSITION = arguments!!.getInt(ARGUMENT_FRAGMENT_POSITION_KEY)
        ARGUMENT_VIN = arguments!!.getString(ARGUMENT_VIN_KEY)
        ARGUMENT_DESCRIPTION = arguments!!.getString(ARGUMENT_DESCRIPTION_KEY)
    }


}