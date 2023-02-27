package activities

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity: AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        initializeView()
        super.onCreate(savedInstanceState)
        configureToolbar()
        configureUI()
        subscribe()
    }

    open fun configureToolbar() {
        this.supportActionBar?.hide()
    }

    open fun initializeView() {}

    open fun configureUI() {}

    open fun subscribe() {}

    protected fun hideKeyboard() {
        val view = this.currentFocus
        if(view != null) {
            val hide = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            hide.hideSoftInputFromWindow(view.windowToken, 0)
        } else {
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }
}