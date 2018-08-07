package com.lordgama.takingcarpicturesutilities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.lordgama.carpicturesutilities.CaptureFlowListener

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CaptureFlowListener, AddVehicleFragment.OnSaveButtonClickListener {

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.right_top_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when(item?.itemId){
            R.id.add_vehicle ->{
                val fragmentManager = supportFragmentManager
                fragmentManager.beginTransaction().replace(R.id.custom,AddVehicleFragment()).commit()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    override fun onClickSave() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.custom,VehiclesFragment()).commit()
    }

    override fun moveToThePage(position: Int) {
        when(position){
            0 -> {
                val fragmentManager = supportFragmentManager
                val argumentos = Bundle()
                argumentos.putInt("ARGUMENT_FRAGMENT_POSITION",0)
                argumentos.putInt("ARGUMENT_PHOTO_TYPE",1)
                val fragment = MCaptureFragment()
                fragment.arguments = argumentos
                fragmentManager.beginTransaction().replace(R.id.custom,fragment).commit()
            }
            1 -> {
                val fragmentManager = supportFragmentManager
                val argumentos = Bundle()
                argumentos.putInt("ARGUMENT_FRAGMENT_POSITION",1)
                argumentos.putInt("ARGUMENT_PHOTO_TYPE",2)
                val fragment = MCaptureFragment()
                fragment.arguments = argumentos
                fragmentManager.beginTransaction().replace(R.id.custom,fragment).commit()
            }
            2 -> {
                val fragmentManager = supportFragmentManager
                val argumentos = Bundle()
                argumentos.putInt("ARGUMENT_FRAGMENT_POSITION",2)
                argumentos.putInt("ARGUMENT_PHOTO_TYPE",3)
                val fragment = MCaptureFragment()
                fragment.arguments = argumentos
                fragmentManager.beginTransaction().replace(R.id.custom,fragment).commit()
            }
            3 -> {
                val fragmentManager = supportFragmentManager
                val argumentos = Bundle()
                argumentos.putInt("ARGUMENT_FRAGMENT_POSITION",3)
                argumentos.putInt("ARGUMENT_PHOTO_TYPE",4)
                val fragment = MCaptureFragment()
                fragment.arguments = argumentos
                fragmentManager.beginTransaction().replace(R.id.custom,fragment).commit()
            }
            4 -> {
                val fragmentManager = supportFragmentManager
                val argumentos = Bundle()
                argumentos.putInt("ARGUMENT_FRAGMENT_POSITION",4)
                argumentos.putInt("ARGUMENT_PHOTO_TYPE",5)
                val fragment = MCaptureFragment()
                fragment.arguments = argumentos
                fragmentManager.beginTransaction().replace(R.id.custom,fragment).commit()
            }
            5 -> {
                val fragmentManager = supportFragmentManager
                val argumentos = Bundle()
                argumentos.putInt("ARGUMENT_FRAGMENT_POSITION",5)
                argumentos.putInt("ARGUMENT_PHOTO_TYPE",6)
                val fragment = MCaptureFragment()
                fragment.arguments = argumentos
                fragmentManager.beginTransaction().replace(R.id.custom,fragment).commit()
            }
        }
    }

    override fun captureComplete() {
        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().replace(R.id.custom,VehiclesFragment()).commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val fragmentManager = supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.custom,VehiclesFragment()).commit()

        /*val argumentos = Bundle()
        argumentos.putInt("ARGUMENT_FRAGMENT_POSITION",0)
        argumentos.putInt("ARGUMENT_PHOTO_TYPE",2)
        val fragment = MCaptureFragment()
        fragment.arguments = argumentos
        fragmentManager.beginTransaction().add(R.id.custom,fragment).commit()*/

        /*fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/
    }

}
