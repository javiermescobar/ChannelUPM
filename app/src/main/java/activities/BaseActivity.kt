package activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.viewbinding.ViewBinding
import com.airbnb.lottie.LottieAnimationView
import com.javier.channelupm.R
import dialogs.ErrorDialogFragment
import utils.AppState
import viewModels.BaseViewModel

abstract class BaseActivity: AppCompatActivity(){

    protected lateinit var baseViewModel: BaseViewModel
    private lateinit var errorDialog: ErrorDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        baseViewModel = BaseViewModel()
        errorDialog = ErrorDialogFragment(this)

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
        val loading = findViewById<LottieAnimationView>(R.id.loading)

        baseViewModel.appState.observe(this, Observer {
            if(it == AppState.LOADING) {
                loading?.let { anim ->
                    anim.visibility = View.VISIBLE
                }
            } else {
                loading?.let { anim ->
                    anim.visibility = View.GONE
                }
                if(it == AppState.ERROR) {
                    errorDialog.show(supportFragmentManager, resources.getString(R.string.error_dialog_tag))
                }
            }
        })
    }
}