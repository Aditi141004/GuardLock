package security

import android.content.Context
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.provider.Settings

class AlarmManagerHelper(private val context: Context) {

    private var mediaPlayer: MediaPlayer? = null
    private val vibrator = context.getSystemService(Vibrator::class.java)
    private val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

    private var isAlarmRunning = false
    private var previousVolume = 0

    fun startAlarm() {
        if (isAlarmRunning) return

        isAlarmRunning = true

        // 🔊 Save current volume
        previousVolume = audioManager.getStreamVolume(AudioManager.STREAM_ALARM)

        // 🔊 Set max volume (works even in silent mode)
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, maxVolume, 0)

        // 🔊 Play alarm sound
        mediaPlayer = MediaPlayer.create(
            context,
            Settings.System.DEFAULT_ALARM_ALERT_URI
        )

        mediaPlayer?.setAudioAttributes(
            android.media.AudioAttributes.Builder()
                .setUsage(android.media.AudioAttributes.USAGE_ALARM)
                .setContentType(android.media.AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        )
        mediaPlayer?.isLooping = true
        mediaPlayer?.start()

        // 📳 Vibration
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val effect = VibrationEffect.createWaveform(longArrayOf(0, 500, 500), 0)
            vibrator.vibrate(effect)
        }
    }

    fun stopAlarm() {
        isAlarmRunning = false

        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null

        vibrator.cancel()

        // 🔊 Restore previous volume
        audioManager.setStreamVolume(AudioManager.STREAM_ALARM, previousVolume, 0)
    }
}