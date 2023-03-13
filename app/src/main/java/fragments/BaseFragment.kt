package fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.javier.channelupm.R
import dialogs.InformationDialogFragment
import viewModels.BaseViewModel

abstract class BaseFragment: Fragment() {

    protected lateinit var baseViewModel: BaseViewModel
    private lateinit var informationDialog: InformationDialogFragment

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseViewModel = BaseViewModel()

        initializeView()
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    open fun initializeView() {}

    open fun subscribe() {}

    protected fun showInformationDialog(informationMessage: Int) {
        informationDialog = InformationDialogFragment(this.requireActivity(), informationMessage)
        informationDialog.show(parentFragmentManager, resources.getString(R.string.information_dialog_tag))
    }
}