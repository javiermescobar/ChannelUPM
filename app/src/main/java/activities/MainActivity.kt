package activities

import com.javier.channelupm.databinding.ActivityMainBinding

class MainActivity: BaseActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun initializeView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}