package activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        initializeView()
        super.onCreate(savedInstanceState)
        configureToolbar()
        subscribe()
    }

    open fun configureToolbar() {
        this.supportActionBar?.hide()
    }

    open fun initializeView() {}

    open fun subscribe() {}
}