package com.example.pruebadeingreso

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.pruebadeingreso.adapter.PageAdapter
import com.example.pruebadeingreso.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var viewPager: ViewPager? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewPager = findViewById(R.id.viewPager)

        checkInternet()

    }

    override fun onResume() {
        super.onResume()
        checkInternet()
    }

    private fun checkInternet(){
        if (isOnline(this)){
            setupViewPager()
        }else{
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Connect to Internet to Continue")
                .setCancelable(false)
                .setPositiveButton("Connect") { dialog, id -> startActivity(Intent(Settings.ACTION_WIFI_SETTINGS)) }
                .setNegativeButton("Quit") { dialog, id -> finish() }
            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }

    private fun setupViewPager(){
        val adapter = PageAdapter(supportFragmentManager)
        viewPager!!.adapter = adapter

        viewPager!!.offscreenPageLimit = 2
    }

    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

}