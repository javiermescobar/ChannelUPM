package activities

import android.content.Intent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.airbnb.lottie.LottieAnimationView
import com.javier.channelupm.R
import com.javier.channelupm.databinding.ActivityLoginBinding
import okhttp3.internal.wait
import repositories.LoginRepository
import utils.Constants
import viewModels.LoginViewModel

class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun initializeView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val loginRepository = LoginRepository()
        loginViewModel = LoginViewModel(loginRepository, super.baseViewModel)
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
            if(it.UserId != -1) {
                Constants.currentUserId = it.UserId
            } else {
                binding.errorTextPassword.visibility = View.VISIBLE

                Thread{
                    Thread.sleep(100)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }.start()
            }
        })
    }
}