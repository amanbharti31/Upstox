package com.example.upstox

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import upstox.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
    }
    override fun onSupportNavigateUp(): Boolean {
        return Navigation.findNavController(this,
            R.id.nav_host_fragment_container)
            .navigateUp() || super.onSupportNavigateUp()
    }
}