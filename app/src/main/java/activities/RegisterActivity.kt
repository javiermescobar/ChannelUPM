package activities

import android.content.Intent
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import com.javier.channelupm.R
import com.javier.channelupm.databinding.ActivityRegisterBinding
import repositories.RegisterRepository
import utils.Constants
import viewModels.RegisterViewModel

class RegisterActivity: BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun initializeView() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerViewModel = RegisterViewModel(RegisterRepository(), super.baseViewModel)
    }

    override fun configureUI() {
        binding.backButton.setOnClickListener {
            super.hideKeyboard()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }

        binding.mailTextBox.addTextChangedListener {
            binding.errorTextMail.visibility = View.GONE
        }

        binding.passwordTextBox.addTextChangedListener {
            binding.errorPassword.visibility =  View.GONE
        }

        binding.confirmPasswordTextBox.addTextChangedListener {
            binding.errorMatchPassword.visibility = View.GONE
        }

        binding.confirmRegistrationButton.setOnClickListener {
            super.hideKeyboard()
            performRegister()
        }
    }

    override fun subscribe() {
        registerViewModel.mutableUserRegistered.observe(this, Observer {
            if(it == 0) {
                val password = binding.passwordTextBox.text.toString()
                if(password.length < Constants.MIN_PASSWORD_LENGHT ||
                    password.length > Constants.MAX_PASSWORD_LENGHT) {
                    binding.errorPassword.text = binding.root.resources.getText(R.string.register_error_password_short)
                    binding.errorPassword.visibility = View.VISIBLE
                } else {
                    if(passwordGoodEnough(password)) {
                        if(password == binding.confirmPasswordTextBox.text.toString()) {
                            registerViewModel.registerUser(
                                name = binding.nameTextBox.text.toString(),
                                mail = binding.mailTextBox.text.toString(),
                                password = password
                            )
                        } else {
                            binding.errorMatchPassword.visibility = View.VISIBLE
                        }
                    } else {
                        binding.errorPassword.text = binding.root.resources.getText(R.string.register_error_password_weak)
                        binding.errorPassword.visibility = View.VISIBLE
                    }
                }
            } else {
                binding.errorTextMail.text = binding.root.resources.getText(R.string.register_error_mail_registered)
                binding.errorTextMail.visibility = View.VISIBLE
            }
        })

        registerViewModel.mutableRegisteredUser.observe(this, Observer {
            registerViewModel.createUserConfiguration(
                if(isDarkThemeOn()) { 0 } else { 1 },
                it.UserId)
        })

        registerViewModel.mutableConfigurationCreated.observe(this, Observer {
            if(it) {
                Constants.userCreated = true
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        })
    }

    private fun performRegister() {

        if(anyBlankField()) {
            super.showInformationDialog(R.string.enter_all_fields, true)
        } else {
            val mail = binding.mailTextBox.text.toString()
            if(mail.contains('@') && Constants.isAcceptedDomain(mail.split('@')[1])) {
                registerViewModel.getMailRegistered(mail)
            } else {
                binding.errorTextMail.text = binding.root.resources.getText(R.string.login_error_no_upm_mail)
                binding.errorTextMail.visibility = View.VISIBLE
            }
        }
    }

    private fun anyBlankField(): Boolean {
        return (binding.nameTextBox.text.isEmpty() ||
                    binding.mailTextBox.text.isEmpty() ||
                    binding.passwordTextBox.text.isEmpty() ||
                    binding.confirmPasswordTextBox.text.isEmpty())
    }

    private fun passwordGoodEnough(password: String): Boolean {
        var hasLow = false
        var hasUpp = false
        var hasNum = false

        password.forEach {
            if(it.isLowerCase() && !hasLow) {
                hasLow = true
            } else if(it.isUpperCase() && !hasUpp) {
                hasUpp = true
            } else if(it.isDigit() && !hasNum) {
                hasNum = true
            }
        }
        return hasLow && hasUpp && hasNum
    }
}