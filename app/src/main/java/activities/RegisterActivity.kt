package activities

import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.Observer
import com.javier.channelupm.R
import com.javier.channelupm.databinding.ActivityRegisterBinding
import okhttp3.internal.wait
import repositories.RegisterRepository
import utils.Constants
import viewModels.RegisterViewModel

class RegisterActivity: BaseActivity() {

    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerRepository: RegisterRepository
    private lateinit var registerViewModel: RegisterViewModel

    private var userRegistered = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureUI()
    }

    override fun initializeView() {
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        registerRepository = RegisterRepository()
        registerViewModel = RegisterViewModel(registerRepository)
    }

    override fun subscribe() {
        registerViewModel.mutableUserRegistered.observe(this, Observer {
            if(it.isSuccessful) {
                userRegistered = it.body() == 0
            }
        })
    }

    private fun configureUI() {
        binding.backButton.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
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
            performRegister()
        }
    }

    private fun performRegister() {
        val mail = binding.mailTextBox.text.toString()

        if(mail.contains('@') && Constants.isAcceptedDomain(mail.split('@')[1])) {
            registerViewModel.getMailRegistered(binding.mailTextBox.text.toString())
            if(!userRegistered) {
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
                                mail = mail,
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
        } else {
            binding.errorTextMail.text = binding.root.resources.getText(R.string.login_error_no_upm_mail)
            binding.errorTextMail.visibility = View.VISIBLE
        }
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