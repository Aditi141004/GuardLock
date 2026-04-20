package viewmodel

import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    var isProtectionActive = false

    private val pin = "1234" // 🔐 Default PIN

    fun activateProtection() {
        isProtectionActive = true
    }

    fun deactivateProtection(inputPin: String): Boolean {
        return if (inputPin == pin) {
            isProtectionActive = false
            true
        } else {
            false
        }
    }
}