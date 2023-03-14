package activities

import android.content.Intent
import com.javier.channelupm.databinding.ActivitySplashBinding

class SplashActivity: BaseActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun initializeView() {
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Thread {
            Thread.sleep(2000)
            startActivity(Intent(this,LoginActivity::class.java))
        }.start()
    }
}