package activities

import android.os.Bundle
import com.javier.channelupm.databinding.ActivityLoginBinding

class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        configureUI()
    }

    private fun configureUI() {
    }
}