package activities

import android.content.Intent
import android.os.Bundle
import com.javier.channelupm.databinding.ActivityLoginBinding

class LoginActivity: BaseActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        configureUI()
    }

    override fun initializeView() {
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun subscribe() {}

    private fun configureUI() {
        binding.registerButton.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}