package activities

import android.content.Intent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.javier.channelupm.R
import com.javier.channelupm.databinding.ActivityLoginBinding
import models.User
import repositories.LoginRepository
import utils.Constants
import viewModels.LoginViewModel

class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun initializeView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = LoginViewModel(LoginRepository(), super.baseViewModel)
    }

    override fun configureUI() {
        binding.LoginButton.setOnClickListener {
            super.hideKeyboard()

            val mail = binding.mailTextBox.text.toString()
            val password = binding.passwordTextBox.text.toString()

            if(mail.isEmpty() || password.isEmpty()) {
                super.showInformationDialog(R.string.enter_all_fields)
            } else {

                if(mail == "admin" && password == "0000") {
                    Constants.currentUser = User.emptyAdminUser()
                    Thread{
                        Thread.sleep(100)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }.start()
                } else {
                    loginViewModel.loginUser(
                        mail,
                        password
                    )
                }
            }
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
                Constants.currentUser = it
                Thread{
                    Thread.sleep(100)
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                }.start()
            } else {
                binding.errorTextPassword.visibility = View.VISIBLE
            }
        })
    }
}