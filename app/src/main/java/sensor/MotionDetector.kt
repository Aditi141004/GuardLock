package sensor

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import kotlin.math.abs

class MotionDetector(context: Context) : SensorEventListener {

    private val sensorManager =
        context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    private val accelerometer: Sensor? =
        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

    var onMotionDetected: (() -> Unit)? = null

    private val threshold = 3

    private var lastX = 0f
    private var lastY = 0f
    private var lastZ = 0f

    private var isFirstReading = true // ✅ FIX

    fun start() {
        isFirstReading = true // reset every time
        accelerometer?.let {
            sensorManager.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    fun stop() {
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event == null) return

        val x = event.values[0]
        val y = event.values[1]
        val z = event.values[2]

        if (isFirstReading) {
            lastX = x
            lastY = y
            lastZ = z
            isFirstReading = false
            return
        }

        val deltaX = abs(lastX - x)
        val deltaY = abs(lastY - y)
        val deltaZ = abs(lastZ - z)

        if (deltaX > threshold || deltaY > threshold || deltaZ > threshold) {
            onMotionDetected?.invoke()
        }

        lastX = x
        lastY = y
        lastZ = z
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
}