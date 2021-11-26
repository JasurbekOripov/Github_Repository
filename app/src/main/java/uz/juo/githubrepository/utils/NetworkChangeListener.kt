package uz.juo.githubrepository.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AlertDialog

class NetworkChangeListener : BroadcastReceiver() {
    private lateinit var internetConnectionHelper: NetworkHelper
    override fun onReceive(context: Context?, intent: Intent?) {
        internetConnectionHelper = NetworkHelper(context!!)
        if (!internetConnectionHelper.isNetworkConnected()) {
            val dialog = AlertDialog.Builder(context)
            dialog.setTitle("Error")
            dialog.setMessage("Please check internet connection or turn on Wi-Fi")
            dialog.setPositiveButton("OK"
            ) { dialog, id ->
                onReceive(context, intent)
            }
            val alertDialog = dialog.create()
            alertDialog.show()
        }
    }

}