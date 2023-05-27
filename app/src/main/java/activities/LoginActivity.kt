package activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.javier.channelupm.R
import com.javier.channelupm.databinding.ActivityLoginBinding
import models.User
import repositories.LoginRepository
import repositories.RegisterRepository
import utils.Constants
import viewModels.LoginViewModel
import viewModels.RegisterViewModel

class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var registerViewModel: RegisterViewModel

    override fun initializeView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loginViewModel = LoginViewModel(LoginRepository(), baseViewModel)
        registerViewModel = RegisterViewModel(RegisterRepository(), baseViewModel)
    }

    override fun configureUI() {
        if(Constants.userCreated) {
            Constants.userCreated = false
            super.showInformationDialog(R.string.user_created, false)
        }
        binding.LoginButton.setOnClickListener {
            super.hideKeyboard()

            val mail = binding.mailTextBox.text.toString()
            val password = binding.passwordTextBox.text.toString()

            if(mail.isEmpty() || password.isEmpty()) {
                super.showInformationDialog(R.string.enter_all_fields, true)
            } else {
                loginViewModel.loginUser(mail, password)
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
            finish()
        }
    }

    override fun subscribe() {
        loginViewModel.currentUser.observe(this, Observer {
            if(it.UserId != -1) {
                Constants.currentUser = it
                registerViewModel.getUserConfigurationById(Constants.currentUser.UserId)
            } else {
                binding.errorTextPassword.visibility = View.VISIBLE
            }
        })

        registerViewModel.mutableCreatedConfiguration.observe(this, Observer {
            Constants.currentUserConfiguration = it
            Constants.performSave = true;
            Thread{
                Thread.sleep(100)
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }.start()
        })
    }
}