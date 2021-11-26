package uz.juo.githubrepository.ui.splash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import uz.juo.githubrepository.R
@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }

    override fun onNavigateUp(): Boolean {
        return Navigation.findNavController(this, R.id.splashFragment).navigateUp()
    }
}