package fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import viewModels.BaseViewModel

abstract class BaseFragment: Fragment() {

    protected lateinit var baseViewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        baseViewModel = BaseViewModel()

        initializeView()
        super.onViewCreated(view, savedInstanceState)
        subscribe()
    }

    open fun initializeView() {}

    open fun subscribe() {}
}