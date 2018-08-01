package com.lordgama.takingcarpicturesutilities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.lordgama.carpicturesutilities.CaptureFlowListener

import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), CaptureFlowListener {
    override fun moveToThePage(position: Int) {
    }

    override fun captureComplete() {
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

}
