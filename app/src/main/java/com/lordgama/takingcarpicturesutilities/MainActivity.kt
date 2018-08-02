package com.lordgama.takingcarpicturesutilities

import android.os.Bundle
import android.support.design.widget.Snackbar
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
    }

    override fun moveToThePage(position: Int) {
    }

    override fun captureComplete() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val fragmentManager = supportFragmentManager

        val argumentos = Bundle()
        argumentos.putInt("ARGUMENT_FRAGMENT_POSITION",0)
        argumentos.putInt("ARGUMENT_PHOTO_TYPE",2)
        val fragment = CustomFragment()
        fragment.arguments = argumentos
        fragmentManager.beginTransaction().add(R.id.custom,fragment).commit()

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
