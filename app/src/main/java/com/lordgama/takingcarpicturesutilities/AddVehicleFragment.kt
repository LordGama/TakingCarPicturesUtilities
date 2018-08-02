package com.lordgama.takingcarpicturesutilities

import android.app.Activity
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_capture_vehicle.*

class AddVehicleFragment: Fragment() {
    var onSaveButtonClickListener: OnSaveButtonClickListener? = null
    lateinit var viewModel: ViewModel

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is Activity) {
            try {
                onSaveButtonClickListener = context as OnSaveButtonClickListener
            } catch (e: ClassCastException) {
                throw ClassCastException(activity.toString() + " must implement OnSaveButtonClickListener")
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_capture_vehicle,container,false)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(ViewModel::class.java)


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_save.setOnClickListener {

            val newVehicle = CustomVehicle()
            newVehicle.customAgentName = text_input_edit_text_custom_agent.text.toString()
            newVehicle.make = text_input_edit_text_make.text.toString()
            newVehicle.model = text_input_edit_text_model.text.toString()
            newVehicle.year = text_input_edit_text_year.text.toString()
            newVehicle.vin = text_input_edit_text_vin.text.toString()
            newVehicle.pediment = text_input_edit_text_pediment.text.toString()

            viewModel.database.customVehicleDao().insertAll(newVehicle)

            onSaveButtonClickListener?.onClickSave()

        }


    }

    interface OnSaveButtonClickListener{
        fun onClickSave()
    }
}