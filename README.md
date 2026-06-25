# 🛡️ GuardLock
![Android](https://img.shields.io/badge/Platform-Android-3DDC84?logo=android\&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?logo=kotlin\&logoColor=white)
![Architecture](https://img.shields.io/badge/Architecture-MVVM-blue)
![IDE](https://img.shields.io/badge/IDE-Android%20Studio-3DDC84?logo=androidstudio\&logoColor=white)
![Status](https://img.shields.io/badge/Status-Completed-success)
![License](https://img.shields.io/badge/License-MIT-yellow)

An Android-based anti-theft application that transforms a smartphone into a motion-sensitive security system. Once activated, the app continuously monitors device movement using the accelerometer sensor and immediately triggers an alarm if unauthorized motion is detected.

Developed as part of the **Mobile Communication** course using **Kotlin** and **Android Studio**.

---

## ✨ Features

* 📱 Motion detection using the device's accelerometer
* 📊 Real-time monitoring of movement along the X, Y, and Z axes
* 🚨 Threshold-based movement detection
* 🔊 High-volume alarm that overrides silent mode
* 📳 Continuous vibration during theft detection
* 🔦 Flashlight blinking for visual alerts
* 🔐 PIN-protected deactivation to prevent unauthorized access
* 🏗️ Built using the MVVM architecture

---

## 📸 Screenshots

| Home Screen          | Monitoring          | Alarm Triggered          |
| -------------------- | ------------------- | ------------------------ |
| ![](home_screen.png) | ![](monitoring.png) | ![](alarm_triggered.png) |

---

## 🛠️ Tech Stack

* **Language:** Kotlin
* **IDE:** Android Studio
* **Architecture:** MVVM (Model-View-ViewModel)

### Android APIs Used

* SensorManager
* ViewModel
* LiveData
* MediaPlayer
* Vibrator API
* Camera/Flashlight API

---

## 🏗️ Project Structure

```text
app/
│
├── MainActivity
├── ViewModel
├── MotionDetector
├── AlarmManagerHelper
├── FlashlightHelper
└── UI Components
```

### Main Components

| Component              | Responsibility                                    |
| ---------------------- | ------------------------------------------------- |
| **MainActivity**       | Handles user interface and user interactions      |
| **ViewModel**          | Manages application state and business logic      |
| **MotionDetector**     | Processes accelerometer data and detects movement |
| **AlarmManagerHelper** | Controls alarm sound and vibration                |
| **FlashlightHelper**   | Manages flashlight blinking                       |

---

## ⚙️ How It Works

1. The user sets a security PIN.
2. Monitoring mode is activated.
3. The application continuously reads accelerometer data.
4. If movement exceeds the predefined threshold:

   * A loud alarm starts playing.
   * The device vibrates continuously.
   * The flashlight begins blinking.
5. The alarm can only be stopped by entering the correct PIN.

---

## 🚀 Installation

1. Clone the repository.

```bash
git clone https://github.com/Aditi141004/GuardLock.git
```

2. Open the project in Android Studio.

3. Sync Gradle dependencies.

4. Build and run the application on an Android device with:

   * Accelerometer sensor
   * Flashlight
   * Android 8.0 (API 26) or above (recommended)

---

## 🔒 Permissions Used

The application requires the following Android permissions:

* Camera (Flashlight)
* Vibration
* Foreground Service (where applicable)

---
## 🚀 Future Improvements

* Background monitoring service
* GPS location tracking
* SMS or email alerts upon theft detection
* Biometric authentication
* Adjustable motion sensitivity
* Cloud backup for security events


