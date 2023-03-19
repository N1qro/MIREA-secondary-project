package com.example.mirea_app

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import android.view.Menu
import android.view.MenuItem
import com.example.mirea_app.databinding.ActivityMainBinding
import com.example.mirea_app.utils.AppNetworkManager

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val netWork = AppNetworkManager(applicationContext)

        binding.btn.setOnClickListener {
            netWork.gettingAFlightAssignment()
        }
        setContentView(binding.root)
    }
}