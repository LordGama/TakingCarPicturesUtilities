package com.lordgama.takingcarpicturesutilities

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.vehicle_item.view.*

class VehiclesAdapter(var vehicles: MutableList<CustomVehicle>): RecyclerView.Adapter<VehiclesAdapter.VehiclesHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VehiclesHolder {
        return VehiclesHolder(LayoutInflater.from(parent.context).inflate(R.layout.vehicle_item,parent,false))
    }

    override fun getItemCount(): Int {
       return vehicles.size
    }

    override fun onBindViewHolder(holder: VehiclesHolder, position: Int) {
        holder.bind(vehicles.get(position))
    }

    class VehiclesHolder(itemView: View?): RecyclerView.ViewHolder(itemView){
        fun bind(vehicle: CustomVehicle){
            itemView.vehicle_title.text = "${vehicle.make} ${vehicle.model} ${vehicle.year}".capitalize()
            itemView.vehicle_vin.text = vehicle.vin
            itemView.vehicle_status.text = vehicle.status.status
        }
    }
}