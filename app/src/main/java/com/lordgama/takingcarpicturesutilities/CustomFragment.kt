package com.lordgama.takingcarpicturesutilities

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lordgama.carpicturesutilities.BaseVehicle
import com.lordgama.carpicturesutilities.VehicleCaptureBaseFragment
import com.lordgama.takingcarpicturesutilities.R.id.text_view_test
import kotlinx.android.synthetic.main.custom_fragment.*
import java.util.zip.Inflater

class CustomFragment: VehicleCaptureBaseFragment<CustomVehicle>() {
    override var AUTHORITY: String = BuildConfig.APPLICATION_ID + ".fileprovider"
    override var vehicle: CustomVehicle = CustomVehicle()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.custom_fragment,container,false) as View
    }

    override fun onMissingPermissions() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onMissingPermissionsDontAskAgain() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onPermissionGranted() {

        text_view_test.setText("${vehicle.id} : ${vehicle.customAgentName}")

        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}