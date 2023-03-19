package com.example.mirea_app

import android.app.Activity
import android.icu.util.IslamicCalendar.CalculationType
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
import kotlin.math.max
import kotlin.math.min

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    var currentDay: Int? = null
        set(value) {
            if (value != field && value!! in minDays..maxDays) {
                field = value
                binding.tvCurrentDayNumber.text = value.toString()
                onDayUpdate(value.toByte(), true)
            }
        }
    var dataLoaded = false
    var maxDays: Int = 0
        set(value) {field = value; binding.tvRouteLength.text = "Маршрут на $value дней"}
    val minDays = 1

    // Потраченные ресурсы за день
    var spentPlants = 0
        set(value) {field = value; binding.tvDeployedPlants.text = this.getString(R.string.item_units, value.toString())}
    var spentFuel = 0
        set(value) {field = value; binding.tvSpentFuel.text = this.getString(R.string.item_units, value.toString())}

    // Оставшиеся ресурсы
    var remainingPlants = 0
        set(value) {field = value; binding.tvRemainingPlants.text = this.getString(R.string.item_units, value.toString())}
    var remainingFuel = 0
        set(value) {field = value; binding.tvRemainingFuel.text = this.getString(R.string.item_units, value.toString())}
    var remainingOxygen = 0
        set(value) {field = value; binding.tvRemainingOxygen.text = this.getString(R.string.item_units, value.toString())}

    // Характеристики
    var shipMass = 0
        set(value) {field = value; binding.tvShipMass.text = this.getString(R.string.weight_unit, value.toString())}
    var roomTemperature = 0
        set(value) {field = value; binding.tvTemperature.text = this.getString(R.string.temp_unit, value.toString())}
    var energyUnits = 0
        set(value) {field = value; binding.tvEnergyAmount.text = this.getString(R.string.item_units, value.toString())}
    var engineThrottle = 0
        set(value) {field = value; binding.tvEngineThrottle.text = this.getString(R.string.percent_unit, engineThrottle.toString())}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val netWork = AppNetworkManager(applicationContext)

        binding.btnFetch.setOnClickListener {
            if (!dataLoaded) {
                fetchData()
                dataLoaded = true
            }
        }

        binding.btnReset.setOnClickListener {
            if (dataLoaded) {
                spentFuel = 0
                currentDay = 0
                spentPlants = 0
                remainingOxygen = 0
                remainingFuel = 0
                remainingPlants = 0
                shipMass = 0
                roomTemperature = 0
                energyUnits = 0
                engineThrottle = 0
                dataLoaded = false
                binding.tvRouteLength.text = "Маршрут не определён"
            }
        }

        binding.btnAdd.setOnClickListener { currentDay = min(currentDay?.plus(1) ?: maxDays, maxDays) }
        binding.btnSub.setOnClickListener { currentDay = max(currentDay?.minus(1) ?: minDays, minDays) }

        setContentView(binding.root)
    }

    private fun onDayUpdate(newDay: Byte, randomValues: Boolean = false) {
        // Эта функция сработает при смене currentDay

        if (randomValues) {
            spentFuel = (100..999).random()
            spentPlants = (50..350).random()
        }
    }

    private fun fetchData() {
        // Функция для загрузки данных из сервера
        generateRandomValues()
        currentDay = 1
    }

    private fun generateRandomValues() {
        maxDays = calculateAmountOfDays()
        spentFuel = (100..999).random()
        spentPlants = (50..350).random()
        remainingPlants = (900..1000).random()
        remainingOxygen = (900..1000).random()
        remainingFuel = (900..1000).random()
        shipMass = (10..150).random()
        roomTemperature = (26..30).random()
        energyUnits = (300..720).random()
        engineThrottle = (20..80).random()
    }

    private fun calculateAmountOfDays(): Int {
        return (5..15).random()
    }
}