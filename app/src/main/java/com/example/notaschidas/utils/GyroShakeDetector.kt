package com.example.notaschidas.utils

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import kotlin.math.abs

class GyroShakeDetector(
    private val onShake: () -> Unit
) : SensorEventListener {

    private var lastTime = 0L

    override fun onSensorChanged(event: SensorEvent) {
        val rotX = abs(event.values[0])
        val rotY = abs(event.values[1])
        val rotZ = abs(event.values[2])

        val velocidadRotacion = rotX + rotY + rotZ
        val tiempoActual = System.currentTimeMillis()

        // Umbral de rotación (ajustable)
        if (velocidadRotacion > 11f && tiempoActual - lastTime > 1200) {
            lastTime = tiempoActual
            onShake()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}