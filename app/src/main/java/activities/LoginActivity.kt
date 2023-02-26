package activities

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.javier.channelupm.R
import com.javier.channelupm.databinding.ActivityLoginBinding
import repositories.LoginRepository
import viewModels.LoginViewModel

class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun initializeView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginRepository = LoginRepository()
        loginViewModel = LoginViewModel(loginRepository)
    }

    override fun configureUI() {
        binding.LoginButton.setOnClickListener {
            super.hideKeyboard()
            loginViewModel.loginUser(
                binding.mailTextBox.text.toString(),
                binding.passwordTextBox.text.toString()
            )
        }

        binding.mailTextBox.addTextChangedListener {
            binding.errorTextPassword.visibility = View.GONE
        }

        binding.passwordTextBox.addTextChangedListener {
            binding.errorTextPassword.visibility = View.GONE
        }

        binding.registerButton.setOnClickListener {
            super.hideKeyboard()
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun subscribe() {
        loginViewModel.currentUser.observe(this, Observer {
            if(it.UserId == -1) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                binding.errorTextPassword.visibility = View.VISIBLE
            }
        })
    }
}