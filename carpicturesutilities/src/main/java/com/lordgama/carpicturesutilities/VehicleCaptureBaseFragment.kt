package com.lordgama.carpicturesutilities
import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.Spanned
import android.util.DisplayMetrics
import android.util.Log
import java.io.File
import java.util.ArrayList
import java.util.HashMap
import java.util.regex.Pattern
import kotlinx.android.synthetic.main.fragment_capture_photo.*

abstract class VehicleCaptureBaseFragment<T>: Fragment() {

    //Default Images
    open val DEFAULT_IMAGE_RESOURCE_VIN = R.drawable.primera_imagen
    open val DEFAULT_IMAGE_RESOURCE_FRONT = R.drawable.cuarta_imagen
    open val DEFAULT_IMAGE_RESOURCE_DOOR = R.drawable.segunda_imagen
    open val DEFAULT_IMAGE_RESOURCE_DSIDE = R.drawable.tercera_imagen
    open val DEFAULT_IMAGE_RESOURCE_PSIDE = R.drawable.tercera_imagen
    open val DEFAULT_IMAGE_RESOURCE_BACK = R.drawable.quinta_imagen
    open var DEFAULT_IMAGE_PLACEHOLDER: Int = R.drawable.image_placeholder

    /*
    Related to Page Navigation
     */
    var captureFlowListener: CaptureFlowListener? = null
    val ARGUMENT_FRAGMENT_POSITION_KEY = "ARGUMENT_FRAGMENT_POSITION"
    var ARGUMENT_FRAGMENT_POSITION: Int = 0


    /*
    Related to Photo
     */
    var photoLabelText = ""


    //Photo File and Key
    val EXTRA_PHOTO_TAKEN_KEY = "EXTRA_PHOTO_TAKEN"
    lateinit var EXTRA_PHOTO_TAKEN: File

    //Photo Type and Key
    val ARGUMENT_PHOTO_TYPE_KEY = "ARGUMENT_PHOTO_TYPE"
    lateinit var ARGUMENT_PHOTO_TYPE: VehiclePhoto.PhotoType

    /*
    Permission
     */
    val REQUEST_ID_MULTIPLE_PERMISSIONS = 1

    //Access to file provider
    abstract var AUTHORITY: String

    val REQUEST_CODE_IMAGE_CAPTURE = 9002


    /*
    Vehicle POJO
     */
    abstract var vehicle: T

    var displaySizeWidth = 0


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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if(arguments!=null){
            ARGUMENT_FRAGMENT_POSITION = arguments!!.getInt(ARGUMENT_FRAGMENT_POSITION_KEY,0)
            ARGUMENT_PHOTO_TYPE = VehiclePhoto.PhotoType.toPhotoType(arguments!!.getInt(ARGUMENT_PHOTO_TYPE_KEY,0)) ?: VehiclePhoto.PhotoType.PREVIEW
        }else{
            ARGUMENT_FRAGMENT_POSITION = 0
            ARGUMENT_PHOTO_TYPE = VehiclePhoto.PhotoType.PREVIEW
        }

        /*
        Get the device Screen white to use
         */
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        displaySizeWidth = displayMetrics.widthPixels

        /*
        Get File From Bundle
         */
        if (savedInstanceState != null){
            val serializable = savedInstanceState.getSerializable(EXTRA_PHOTO_TAKEN_KEY)
            if(serializable!=null)
                EXTRA_PHOTO_TAKEN= serializable as File
        }

        setToInitialState()


    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if(::EXTRA_PHOTO_TAKEN.isInitialized)
            outState.putSerializable(EXTRA_PHOTO_TAKEN_KEY,EXTRA_PHOTO_TAKEN)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        if (checkAndRequestPermissions()) {
            onPermissionGranted()
        }
    }

    /**
     * According to the type of photo, the image and text used are set.
     */
    fun setToInitialState(){
        when(ARGUMENT_PHOTO_TYPE){
            VehiclePhoto.PhotoType.PREVIEW -> {}
            VehiclePhoto.PhotoType.VIN -> {
                DEFAULT_IMAGE_PLACEHOLDER = DEFAULT_IMAGE_RESOURCE_VIN
                photoLabelText = "VIN"
            }
            VehiclePhoto.PhotoType.FRONT -> {
                DEFAULT_IMAGE_PLACEHOLDER = DEFAULT_IMAGE_RESOURCE_FRONT
                photoLabelText = "FRONT"
            }
            VehiclePhoto.PhotoType.PASSENGER_SIDE -> {
                DEFAULT_IMAGE_PLACEHOLDER = DEFAULT_IMAGE_RESOURCE_PSIDE
                photoLabelText = "PASSENGER_SIDE"
            }
            VehiclePhoto.PhotoType.BACK -> {
                DEFAULT_IMAGE_PLACEHOLDER = DEFAULT_IMAGE_RESOURCE_BACK
                photoLabelText = "BACK"
            }
            VehiclePhoto.PhotoType.DRIVER_SIDE -> {
                DEFAULT_IMAGE_PLACEHOLDER = DEFAULT_IMAGE_RESOURCE_DSIDE
                photoLabelText = "DRIVER_SIDE"
            }
            VehiclePhoto.PhotoType.DOOR -> {
                DEFAULT_IMAGE_PLACEHOLDER = DEFAULT_IMAGE_RESOURCE_DOOR
                photoLabelText = "DOOR_VIN"
            }
        }
    }

    /**
     * Look for permissions, if they're not asked for them.
     * @return true if all permissions are granted
     */
    fun checkAndRequestPermissions(): Boolean {
        val permissionCamera = ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.CAMERA)
        val locationPermission = ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION)
        val permissionStorage = ContextCompat.checkSelfPermission(activity!!, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        val listPermissionsNeeded = ArrayList<String>()

        if (permissionCamera != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.CAMERA)
        }
        if (locationPermission != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.ACCESS_FINE_LOCATION)
        }
        if (permissionStorage != PackageManager.PERMISSION_GRANTED) {
            listPermissionsNeeded.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }

        if (!listPermissionsNeeded.isEmpty()) {
            requestPermissions(listPermissionsNeeded.toTypedArray(), REQUEST_ID_MULTIPLE_PERMISSIONS)
            return false
        }
        return true
    }


    /**
     * Evaluate the result of the permission dialog
     */
    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        Log.d(ContentValues.TAG, "Permission callback called-------")
        when (requestCode) {
            REQUEST_ID_MULTIPLE_PERMISSIONS -> {

                val perms = HashMap<String, Int>()
                // Initialize the map with both permissions
                perms.put(android.Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED)
                perms.put(android.Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED)
                perms.put(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, PackageManager.PERMISSION_GRANTED)
                // Fill with actual results from user
                if (grantResults.size > 0) {
                    for (i in permissions.indices)
                        perms.put(permissions[i], grantResults[i])
                    // Check for both permissions
                    if (perms[android.Manifest.permission.CAMERA] == PackageManager.PERMISSION_GRANTED
                            && perms[android.Manifest.permission.ACCESS_FINE_LOCATION] == PackageManager.PERMISSION_GRANTED
                            && perms[android.Manifest.permission.WRITE_EXTERNAL_STORAGE] == PackageManager.PERMISSION_GRANTED) {
                        Log.d(ContentValues.TAG, "services permission granted")

                        onPermissionGranted()

                    } else {
                        Log.d(ContentValues.TAG, "Some permissions are not granted ask again ")
                        //permission is denied (this is the first time, when "never ask again" is not checked) so ask again explaining the usage of permission
                        //                        // shouldShowRequestPermissionRationale will return true
                        //show the dialog or snackbar saying its necessary and try again otherwise proceed with setup.
                        if (ActivityCompat.shouldShowRequestPermissionRationale(activity!!, android.Manifest.permission.CAMERA) || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, android.Manifest.permission.ACCESS_FINE_LOCATION) || ActivityCompat.shouldShowRequestPermissionRationale(activity!!, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                            showDialogOK("Camera, Storage and Location Services Permission required for this app",
                                    DialogInterface.OnClickListener { dialog, which ->
                                        when (which) {
                                            DialogInterface.BUTTON_POSITIVE -> checkAndRequestPermissions()
                                            DialogInterface.BUTTON_NEGATIVE -> {
                                                onMissingPermissions()
                                            }
                                        }
                                    })
                        } else {
                            onMissingPermissionsDontAskAgain()
                        }//permission is denied (and never ask again is  checked)
                        //shouldShowRequestPermissionRationale will return false
                    }
                }
            }
        }

    }

    /**
     * User Granted Permissions
     * */
    abstract fun onPermissionGranted()

    /**
     * Still a missing permission
     */
    abstract fun onMissingPermissions()

    /**
     * A permission is missing for the user to fill in the 'do not ask again' box
     */
    abstract fun onMissingPermissionsDontAskAgain()

    /*
    * Wrapper For Dialog
    * */
    private fun showDialogOK(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(context!!)
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", okListener)
                .create()
                .show()
    }
}