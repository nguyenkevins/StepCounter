package com.outbit.step

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.mikhaellopez.circularprogressbar.CircularProgressBar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), SensorEventListener {

    private var allowBackgroundRun = true
    private var setmax = 10000
    private var previousSetMax = 10000
    private var sensorManager: SensorManager? = null
    private var running = false
    private var totalSteps = 0
    private var previousTotalSteps = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d("Main Activity", "onCreate start")

        checkPermission("android.permission.ACTIVITY_RECOGNITION", 1)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        running = true

        loadData()
        saveData()
        resetSteps()
        toggleMax()


        setmax = previousSetMax.toInt()
        val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
        stepcounter.apply {
            setProgressWithAnimation(currentSteps.toFloat())

            textview_maxSteps.text = ("/ " +setmax)
            stepcounter.progressMax = setmax.toFloat()
        }

        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        //val sensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
        Log.d("Main Activity", "onCreate end")


    }

    // Function to check and request permission.
    private fun checkPermission(permission: String, requestCode: Int) {
        if (ContextCompat.checkSelfPermission(this@MainActivity, permission) == PackageManager.PERMISSION_DENIED) {
            // Requesting the permission
            ActivityCompat.requestPermissions(this@MainActivity, arrayOf(permission), requestCode)
        } else {
            Toast.makeText(this@MainActivity, "Permission already granted", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        saveData()
        loadData()
        // use running app in background
        running = allowBackgroundRun
    }

    override fun onBackPressed() {
        super.onBackPressed()
        saveData()
        loadData()
        // use running app in background
        running = allowBackgroundRun
    }

    override fun onDestroy() {
        super.onDestroy()
        saveData()
        loadData()
        // since app destroyed, make running off
        running = allowBackgroundRun
    }

    override fun onResume() {

        super.onResume()
        running = true

        // Either Step Counter or Step Detector
        // Step Counter : More latency, but more accuracy
        // Step Detector: Less latency, but less accuracy
        val stepSensor = sensorManager?.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        if (stepSensor == null) {
            Toast.makeText(this, "There is no sensor on this device.", Toast.LENGTH_SHORT).show()
        } else {

            // SENSOR_DELAY_FASTEST: Get sensor data as fast as possible
            // SENSOR_DELAY_GAME   : Get sensor for games
            // SENSOR_DELAY_NORMAL : Suitable for screen orientation changes
            // SENSOR_DELAY_UI     : Suitable for the user interface
            sensorManager?.registerListener(this, stepSensor, SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
        Log.d("Main Activity", "Accuracy Changed")
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if(running) {
            totalSteps = event!!.values[0].toInt()
            val currentSteps = totalSteps.toInt() - previousTotalSteps.toInt()
            textview_currentSteps.text = ("$currentSteps STEPS")

            stepcounter.apply {
                setProgressWithAnimation(currentSteps.toFloat())
            }

            if(currentSteps.toDouble() >= setmax.toDouble()) {
                textview_quote.text = ("FINISHED")
            } else if((currentSteps.toDouble() / setmax.toDouble() >= 0.75)) {
                textview_quote.text = ("ALMOST DONE")
            } else if((currentSteps.toDouble() / setmax.toDouble() >= 0.55)) {
                textview_quote.text = ("KEEP GOING")
            } else if((currentSteps.toDouble() / setmax.toDouble() >= 0.5)) {
                textview_quote.text = ("HALF WAY")
            } else if((currentSteps.toDouble() / setmax.toDouble() > 0)) {
                textview_quote.text = ("KEEP MOVING")
            } else {
                textview_quote.text = ("START MOVING")
            }
            saveData()
            loadData()
        }


    }

    fun toggleMax() {
        button_max.setOnClickListener {
            if(setmax == 20000) {
                setmax = 100
                textview_maxSteps.text = ("/ 100")
                stepcounter.progressMax = 100.toFloat()
            } else if(setmax == 15000) {
                setmax = 20000
                textview_maxSteps.text = ("/ 20000")
                stepcounter.progressMax = 20000.toFloat()
            } else if(setmax == 10000) {
                setmax = 15000
                textview_maxSteps.text = ("/ 15000")
                stepcounter.progressMax = 15000.toFloat()
            } else if(setmax == 5000) {
                setmax = 10000
                textview_maxSteps.text = ("/ 10000")
                stepcounter.progressMax = 10000.toFloat()
            } else if(setmax == 100) {
                setmax = 5000
                textview_maxSteps.text = ("/ 5000")
                stepcounter.progressMax = 5000.toFloat()
            }
            saveData()
            println(setmax)
            true
        }
    }

    fun resetSteps() {
        textview_currentSteps.setOnClickListener {
            Toast.makeText(this, "App Compiled by: Kevin Nguyen", Toast.LENGTH_SHORT).show()

        }


//        textview_currentSteps.setOnLongClickListener {
//
//        }

        button_reset.setOnClickListener {
            previousTotalSteps = totalSteps
            textview_currentSteps.text = 0.toString() + " STEPS"

            stepcounter.apply {
                setProgressWithAnimation(0.toFloat())
                clearPref()
                saveData()
                loadData()
            }
            textview_quote.text = ("START MOVING")
            loadData()
            true
        }
    }

    private fun clearPref() {
        val sharedPreferences = getSharedPreferences("savedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        // Clearing all data from Shared Preferences
        editor.clear() // where editor is an Editor for Shared Preferences
        editor.commit()
    }

    // editor.commit() not as reliable?
    private fun saveDataCommit() {
        val sharedPreferences = getSharedPreferences("savedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("prefkey", previousTotalSteps.toFloat())
        editor.putInt("maxkey", setmax)
        editor.commit()
    }

    private fun saveData() {
        val sharedPreferences = getSharedPreferences("savedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat("prefkey", previousTotalSteps.toFloat())
        editor.putInt("maxkey", setmax)
        editor.apply()

    }

    private fun loadData() {
        val sharedPreferences = getSharedPreferences("savedPref", Context.MODE_PRIVATE)
        val savedNumber = sharedPreferences.getFloat("prefkey", 0f)
        val savedNumber2 = sharedPreferences.getInt("maxkey", 0)
        previousTotalSteps = savedNumber.toInt()
        previousSetMax = savedNumber2
        //Log.d("Main Activity", "$savedNumber")

    }
}