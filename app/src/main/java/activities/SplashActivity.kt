package activities

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Observer
import com.javier.channelupm.R
import com.javier.channelupm.databinding.ActivitySplashBinding
import repositories.LoginRepository
import repositories.RegisterRepository
import utils.Constants
import viewModels.LoginViewModel
import viewModels.RegisterViewModel

@SuppressLint("CustomSplashScreen")
class SplashActivity: BaseActivity() {

    private lateinit var binding: ActivitySplashBinding
    private lateinit var loginViewModel: LoginViewModel
    private lateinit var registerViewModel: RegisterViewModel

    override fun initializeView() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loginViewModel = LoginViewModel(LoginRepository(), baseViewModel)
        registerViewModel = RegisterViewModel(RegisterRepository(), baseViewModel)
        val sharedPref = getSharedPreferences("label", 0)
        val loggedId = sharedPref.getInt(getString(R.string.saved_user_id_key), -1)
        if(loggedId != -1) {
            with(sharedPref.edit()) {
                remove(getString(R.string.saved_user_id_key))
                commit()
            }
            loginViewModel.getUserById(loggedId)
        } else {
            goToLogin()
        }
    }

    private fun goToLogin() {
        Thread {
            Thread.sleep(2000)
            startActivity(Intent(this,LoginActivity::class.java))
            finish()
        }.start()
    }

    private fun goToMain() {
        Thread{
            Thread.sleep(1000)
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }.start()
    }

    override fun subscribe() {
        loginViewModel.currentUser.observe(this, Observer {
            if(it.UserId != -1) {
                Constants.currentUser = it
                registerViewModel.getUserConfigurationById(Constants.currentUser.UserId)
            } else {
                goToLogin();
            }
        })

        registerViewModel.mutableCreatedConfiguration.observe(this, Observer {
            Constants.currentUserConfiguration = it
            goToMain()
        })
    }
}