package utils

import android.content.Context
import android.hardware.camera2.CameraManager
import android.os.Handler
import android.os.Looper

class FlashlightHelper(context: Context) {

    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager
    private val cameraId = cameraManager.cameraIdList[0]

    private var isBlinking = false
    private val handler = Handler(Looper.getMainLooper())

    private val blinkRunnable = object : Runnable {
        override fun run() {
            if (!isBlinking) return

            toggleFlash(true)
            handler.postDelayed({
                toggleFlash(false)
            }, 200)

            handler.postDelayed(this, 500)
        }
    }

    fun startBlinking() {
        if (isBlinking) return
        isBlinking = true
        handler.post(blinkRunnable)
    }

    fun stopBlinking() {
        isBlinking = false
        handler.removeCallbacks(blinkRunnable)
        toggleFlash(false)
    }

    private fun toggleFlash(state: Boolean) {
        cameraManager.setTorchMode(cameraId, state)
    }
}