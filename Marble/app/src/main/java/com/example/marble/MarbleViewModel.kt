package com.example.marble

import android.app.Application
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class MarbleViewModel(application: Application) : AndroidViewModel(application),
    SensorEventListener {
    // LiveData holding the marble's position
    private val _position = MutableLiveData(Offset.Zero)
    val position: LiveData<Offset> = _position

    // Sensor manager and gravity sensor
    private val sensorManager = application.getSystemService(Context.SENSOR_SERVICE) as SensorManager
    private val gravitySensor = sensorManager.getDefaultSensor(Sensor.TYPE_GRAVITY)

    init {
        // Register the gravity sensor listener with SENSOR_DELAY_GAME
        sensorManager.registerListener(this, gravitySensor, SensorManager.SENSOR_DELAY_GAME)
    }

    override fun onSensorChanged(event: SensorEvent) {
        // Invert the sensor values to match the Compose coordinate system
        val x = event.values[0] * -10f
        val y = event.values[1] * 10f

        // Update the LiveData with the new position
        _position.value = Offset(x, y)
    }

    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
        // Not required for this task
    }

    override fun onCleared() {
        super.onCleared()
        // Unregister the sensor listener when the ViewModel is cleared
        sensorManager.unregisterListener(this)
    }
}