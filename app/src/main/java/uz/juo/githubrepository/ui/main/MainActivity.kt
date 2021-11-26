package uz.juo.githubrepository.ui.main

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import dagger.hilt.android.AndroidEntryPoint
import uz.juo.githubrepository.R
import uz.juo.githubrepository.utils.NetworkChangeListener

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    val br: BroadcastReceiver = NetworkChangeListener()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val filter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION).apply {
            addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        }
        registerReceiver(br, filter)
    }

    override fun onNavigateUp(): Boolean {
        return Navigation.findNavController(this,R.id.homeFragment).navigateUp()
    }
}