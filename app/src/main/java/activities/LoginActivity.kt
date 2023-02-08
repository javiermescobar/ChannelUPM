package activities

import android.os.Bundle
import android.widget.EditText
import com.javier.channelupm.R

class LoginActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureUI()
    }

    override fun setResourceLayout(): Int = R.layout.activity_login

    private fun configureUI() {

    }
}