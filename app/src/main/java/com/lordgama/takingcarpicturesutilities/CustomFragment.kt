package com.lordgama.takingcarpicturesutilities

import android.app.Activity
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.lordgama.carpicturesutilities.BaseVehicle
import com.lordgama.carpicturesutilities.ImageHandler
import com.lordgama.carpicturesutilities.VehicleCaptureBaseFragment
import com.lordgama.carpicturesutilities.VehiclePhoto
import com.lordgama.takingcarpicturesutilities.R.id.text_view_test
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.custom_fragment.*
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*
import java.util.zip.Inflater

class CustomFragment: VehicleCaptureBaseFragment<CustomVehicle>() {
    override var AUTHORITY: String = BuildConfig.APPLICATION_ID + ".fileprovider"
    override var vehicle: CustomVehicle = CustomVehicle()

    lateinit var viewModel: ViewModel

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


        viewModel = ViewModelProviders.of(activity!!).get(ViewModel::class.java)
        viewModel.vehicleToBeCaptured.observe(this, Observer {vehiclec ->
            if(vehiclec!=null){
                vehicle = vehiclec

                text_view_test.setText("$photoLabelText")

                if(ARGUMENT_FRAGMENT_POSITION == 0) button_back.visibility = View.INVISIBLE
                button_back.setOnClickListener{
                    captureFlowListener?.moveToThePage(ARGUMENT_FRAGMENT_POSITION - 1)
                }

                button_next.setOnClickListener{
                    if(ARGUMENT_FRAGMENT_POSITION < 5)
                        captureFlowListener?.moveToThePage(ARGUMENT_FRAGMENT_POSITION + 1)
                    else
                        captureFlowListener?.captureComplete()
                }

                loadPhoto()
            }
        })


    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        button_take_photo.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(activity!!.packageManager) != null) {
                try {
                    EXTRA_PHOTO_TAKEN = ImageHandler.createImageFileJPG(it.context,"savedImage")
                    val photoURI = FileProvider.getUriForFile(it.context,AUTHORITY, EXTRA_PHOTO_TAKEN)//AUTHORITY
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureIntent.putExtra("aspectX", 1);
                    takePictureIntent.putExtra("aspectY", 1);
                    takePictureIntent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION or Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    startActivityForResult(takePictureIntent, REQUEST_CODE_IMAGE_CAPTURE)
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)


        if (requestCode == REQUEST_CODE_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK){//&& data!=null
            doAsync {

                var photoToUpdateInsert: VehiclePhoto? = null
                try {
                    val calendar = Calendar.getInstance()
                    val mdformat = SimpleDateFormat("dd / MM / yyyy ", Locale.ENGLISH)
                    val strDate = mdformat.format(calendar.getTime())

                    photoToUpdateInsert = MPhoto()
                    photoToUpdateInsert.vehicle = vehicle.id
                    photoToUpdateInsert.photoUrlString = EXTRA_PHOTO_TAKEN.absolutePath
                    photoToUpdateInsert.type = ARGUMENT_PHOTO_TYPE
                    photoToUpdateInsert.date = strDate


                    val idPhoto = viewModel.database.mPhotoDao().insert(photoToUpdateInsert)

                    if(idPhoto.toInt() == -1 ){

                        val previousPhoto = viewModel.database.mPhotoDao().getPhoto(photoToUpdateInsert.vehicle,photoToUpdateInsert.type.type).get(0)

                        photoToUpdateInsert.id = previousPhoto.id
                        val previousImageFile = File(previousPhoto.photoUrlString)
                        previousImageFile.delete()

                        viewModel.database.mPhotoDao().update(photoToUpdateInsert)

                        loadPhoto()
                    }

                }catch (e: Exception){
                    Log.e("Error al Actualizar",e.toString())
                }

                uiThread {
                    /*text_view_date.setText(photoToUpdateInsert?.date)
                    text_view_latitude.setText("${photoToUpdateInsert?.latitude}")
                    text_view_longitude.setText("${photoToUpdateInsert?.longitude}")*/
                }
            }
        }else{
            Log.e("Camera Result", "$requestCode $resultCode")
        }
    }

    fun loadPhoto(){

        viewModel.database.mPhotoDao().getPhoto2(vehicle.id,ARGUMENT_PHOTO_TYPE.type).observe(this, Observer {
            image_view_photo.setImageDrawable(ContextCompat.getDrawable(context!!,DEFAULT_IMAGE_PLACEHOLDER))
            for(photo in it.orEmpty()){

                if(!photo.photoUrlString.equals(""))
                    Picasso.with(context).load("file:///"+photo.photoUrlString).centerCrop().resize(displaySizeWidth,(displaySizeWidth*.875).toInt() /* 8:7 ver layout */).placeholder(R.drawable.loading_image).error(DEFAULT_IMAGE_PLACEHOLDER).into(image_view_photo)

            }
        })

    }
}