package com.lordgama.takingcarpicturesutilities

import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lordgama.carpicturesutilities.DefaultItemDecoration
import kotlinx.android.synthetic.main.fragment_vehicles_list.*

class VehiclesListFragment: Fragment() {

    lateinit var viewModel: ViewModel
    var vehiclesList : MutableList<CustomVehicle> = mutableListOf()

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
        recycler_view_vehicles.adapter = VehiclesAdapter(vehiclesList)

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