package com.lordgama.takingcarpicturesutilities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lordgama.carpicturesutilities.CaptureFlowListener
import com.lordgama.carpicturesutilities.DefaultItemDecoration
import kotlinx.android.synthetic.main.fragment_vehicles_list.*

class VehiclesListFragment: Fragment() {

    var captureFlowListener: CaptureFlowListener? = null
    lateinit var viewModel: ViewModel
    var vehiclesList : MutableList<CustomVehicle> = mutableListOf()

    /**
     * Instantiate the listener with the context of the fragment
     */
    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Activity) {
            try {
                captureFlowListener = context as CaptureFlowListener
            } catch (e: ClassCastException) {
                throw ClassCastException(activity.toString() + " must implement CaptureFlowListener")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_vehicles_list,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(ViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_vehicles.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        recycler_view_vehicles.addItemDecoration(DefaultItemDecoration(context!!))


        val adapter = VehiclesAdapter(vehiclesList)
        adapter.setOnVehicleEventListener(object : VehiclesAdapter.OnVehicleEventListener {
            override fun onCapture(vehicle: CustomVehicle) {

                viewModel.setVehicleToBeCaptured(vehicle)
                captureFlowListener?.moveToThePage(0)

            }
        })



        recycler_view_vehicles.adapter = adapter


        viewModel.getVehicles().observe(this, Observer {vehicles ->
            vehiclesList.clear()
            recycler_view_vehicles.adapter.notifyDataSetChanged()

            for(vehicle in vehicles.orEmpty()){
                vehiclesList.add(vehicle)
            }

            recycler_view_vehicles.adapter.notifyDataSetChanged()


        })


    }


}