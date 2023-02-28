package activities

import android.app.Activity
import android.os.Bundle
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import utils.AppState
import viewModels.BaseViewModel

abstract class BaseActivity: AppCompatActivity(){

    protected lateinit var baseViewModel: BaseViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        baseViewModel = BaseViewModel()

        initializeView()
        super.onCreate(savedInstanceState)
        configureToolbar()
        configureUI()
        appStateSubscribe()
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

    private fun appStateSubscribe() {
        baseViewModel.appState.observe(this, Observer {
            if(it == AppState.LOADING) {
                //Show loading animation
            } else {
                //Hide loading animation
                if(it == AppState.ERROR) {
                  //Show error dialog
                }
            }
        })
    }
}