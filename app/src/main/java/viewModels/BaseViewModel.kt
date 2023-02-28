package viewModels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import utils.AppState

class BaseViewModel: ViewModel() {
    val appState: MutableLiveData<AppState> = MutableLiveData()
}