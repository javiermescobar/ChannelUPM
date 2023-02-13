package activities

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.javier.channelupm.databinding.ActivityLoginBinding
import repositories.UserRepository
import viewModels.UserViewModel

class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var userViewModel: UserViewModel

    private val userRepository = UserRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        super.onCreate(savedInstanceState)
    }

    override fun initializeView() {
        subscribe()
        configureUI()
    }

    override fun subscribe() {
        userViewModel = UserViewModel(userRepository)

        userViewModel.user.observe(this, Observer {
            binding.mailTextBox.setText(it.name)
        })
    }

    private fun configureUI() {
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.LoginButton.setOnClickListener {
            userViewModel.getUser()
        }
    }
}