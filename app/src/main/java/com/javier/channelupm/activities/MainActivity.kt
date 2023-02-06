package com.javier.channelupm.activities

import android.os.Bundle
import android.widget.TextView
import com.javier.channelupm.R

class MainActivity: BaseActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<TextView>(R.id.base_text).text = "This is the main activity"
    }

    override fun getLayoutResource(): Int = R.layout.activity_base
}