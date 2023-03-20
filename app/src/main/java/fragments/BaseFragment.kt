package fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.javier.channelupm.R
import dialogs.ErrorDialogFragment
import dialogs.InformationDialogFragment
import utils.AppState
import viewModels.BaseViewModel

abstract class BaseFragment: Fragment() {

    protected lateinit var baseViewModel: BaseViewModel
    private lateinit var informationDialog: InformationDialogFragment
    private lateinit var errorDialogFragment: ErrorDialogFragment
    private lateinit var loading: LottieAnimationView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseViewModel = BaseViewModel()
        errorDialogFragment = ErrorDialogFragment(this.requireActivity())

        loading = view.findViewById(R.id.loading)

        initializeView()
        super.onViewCreated(view, savedInstanceState)
        subscribe()
        appStateSubscribe()
    }

    open fun initializeView() {}

    open fun subscribe() {}

    private fun appStateSubscribe() {

        baseViewModel.appState.observe(requireActivity(), Observer {
            if(it == AppState.LOADING) {
                loading.visibility = View.VISIBLE
            } else {
                loading.visibility = View.GONE
                if(it == AppState.ERROR) {
                    errorDialogFragment.show(parentFragmentManager, resources.getString(R.string.error_dialog_tag))
                }
            }
        })
    }

    protected fun showInformationDialog(informationMessage: Int, isWarning: Boolean) {
        informationDialog = InformationDialogFragment(this.requireActivity(), informationMessage, isWarning)
        informationDialog.show(parentFragmentManager, resources.getString(R.string.information_dialog_tag))
    }
}