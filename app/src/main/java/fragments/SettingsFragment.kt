package fragments

import activities.LoginActivity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.Observer
import com.javier.channelupm.databinding.FragmentSettingsBinding
import repositories.RegisterRepository
import utils.Constants
import viewModels.RegisterViewModel

class SettingsFragment: BaseFragment() {

    private lateinit var binding: FragmentSettingsBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun initializeView() {
        registerViewModel = RegisterViewModel(RegisterRepository(), baseViewModel)

        binding.apply {
            themeSwitch.isChecked = Constants.currentUserConfiguration.Theme == 1

            themeSwitch.setOnCheckedChangeListener { _, isChecked ->
                if(isChecked) {
                    registerViewModel.updateUserConfiguration(1,
                    Constants.currentUserConfiguration.Notifications,
                    Constants.currentUserConfiguration.ConfigId)
                } else {
                    registerViewModel.updateUserConfiguration(0,
                        Constants.currentUserConfiguration.Notifications,
                        Constants.currentUserConfiguration.ConfigId)
                }
            }

            logoutImage.setOnClickListener {
                Constants.performSave = false
                startActivity(Intent(context, LoginActivity::class.java))
                activity?.finish()
            }
        }
    }

    override fun subscribe() {
        registerViewModel.mutableCreatedConfiguration.observe(this, Observer {
            Constants.currentUserConfiguration = it
            if(it.Theme == 1) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
        })
    }
}