package com.example.anti_theft

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import sensor.MotionDetector
import viewmodel.MainViewModel
import security.AlarmManagerHelper
import utils.FlashlightHelper

class MainActivity : AppCompatActivity() {

    private lateinit var flashlightHelper: FlashlightHelper
    private lateinit var alarmHelper: AlarmManagerHelper
    private lateinit var motionDetector: MotionDetector
    private val viewModel: MainViewModel by viewModels()

    private var isAwaitingPin = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val statusText = findViewById<TextView>(R.id.txtStatus)
        val statusSub = findViewById<TextView>(R.id.txtStatusSub)
        val statusDot = findViewById<View>(R.id.viewStatusDot)
        val statusBadge = findViewById<TextView>(R.id.txtStatusBadge)

        val button = findViewById<Button>(R.id.btnToggle)
        val pinInput = findViewById<EditText>(R.id.etPin)

        pinInput.visibility = View.GONE

        flashlightHelper = FlashlightHelper(this)
        alarmHelper = AlarmManagerHelper(this)
        motionDetector = MotionDetector(this)

        // 🚨 Motion Trigger
        motionDetector.onMotionDetected = {
            runOnUiThread {
                statusText.text = "🚨 THEFT ALERT!"
                statusSub.text = "Device moved! Alarm triggered"

                alarmHelper.startAlarm()
                flashlightHelper.startBlinking()
                motionDetector.stop()
            }
        }

        button.setOnClickListener {

            if (viewModel.isProtectionActive) {

                if (!isAwaitingPin) {
                    // 🔐 Show PIN prompt
                    isAwaitingPin = true
                    pinInput.visibility = View.VISIBLE

                    statusSub.text = "Enter PIN to deactivate"
                    button.text = "Submit PIN"
                    return@setOnClickListener
                }

                val enteredPin = pinInput.text.toString()

                if (viewModel.deactivateProtection(enteredPin)) {
                    // ✅ Correct PIN
                    motionDetector.stop()
                    alarmHelper.stopAlarm()
                    flashlightHelper.stopBlinking()

                    button.text = "Activate Protection"
                    statusText.text = "Status: OFF"
                    statusSub.text = "Place phone and tap Activate"

                    // 🔴 UI OFF state
                    statusDot.setBackgroundResource(R.drawable.ic_dot_inactive)
                    statusBadge.text = "OFF"
                    statusBadge.setTextColor(
                        ContextCompat.getColor(this, R.color.status_inactive)
                    )

                    pinInput.text.clear()
                    pinInput.visibility = View.GONE
                    isAwaitingPin = false

                } else {
                    // ❌ Wrong PIN
                    statusSub.text = "❌ Incorrect PIN"
                }

            } else {
                // ✅ Activate
                viewModel.activateProtection()
                motionDetector.start()

                button.text = "Deactivate Protection"
                statusText.text = "Status: ON"
                statusSub.text = "Monitoring for movement..."

                // 🟢 UI ON state
                statusDot.setBackgroundResource(R.drawable.ic_dot_active)
                statusBadge.text = "ON"
                statusBadge.setTextColor(
                    ContextCompat.getColor(this, R.color.status_active)
                )
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}