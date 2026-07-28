<div align="center">
  <img src="/app/src/main/res/mipmap-hdpi/ic_launcher.png" alt="icon"/>
  <br>
  <h1>🍃 Zen Launcher</h1>
</div>
<br>
<p align="center">
  <a href="https://github.com/indraamani/ZenLauncher/blob/main/LICENSE"><img src="https://img.shields.io/badge/License-GPLv3-blue?style=for-the-badge" alt="License"></a>
  <a href="https://github.com/indraamani/ZenLauncher/releases/latest/download/app-release.apk"><img src="https://img.shields.io/badge/Download-APK-brightgreen?style=for-the-badge&logo=android" alt="Download APK"></a>
</p>

<p align="center">
  <b>Master your screen time. Secure your privacy. Simplify your phone.</b>
</p>

# 

## 📋 Table of Contents

- [Overview](#-overview)
- [Key Features](#-key-features)
- [Screenshots](#-screenshots)
    - [1. Three Core Modes](#1-three-core-modes)
    - [2. Privacy & Secret App Cloning](#2-privacy--secret-app-cloning)
    - [3. Focus Tools & Gestures](#3-focus-tools--gestures)
- [Installation](#-installation)
- [Android Build Process](#%EF%B8%8F-android-build-process)
    - [Building via Command Line (Gradle)](#building-via-command-line-gradle)
- [License & Contribution Policy](#-license--contribution-policy)

#

## ⚡ Overview

**Zen Launcher** is a minimalist, privacy-focused Android launcher designed to help you reduce screen time without sacrificing accessibility or control. Whether you want a text-only interface for deep focus, smooth everyday navigation, or password-protected hidden spaces, Zen Launcher delivers it all in one clean package.

#

## 🔥 Key Features

| Feature | Description |
| :--- | :--- |
| **3 Core Modes** | **Text-Only Mode** (Digital Detox), **Normal Mode**, and **Drawer Mode**. |
| **Secret App Clones** | Create isolated, password-protected app clones accessible only via secret gestures. |
| **Focus Lock** | Lock distracting apps for specified durations to eliminate mindless scrolling. |
| **Advanced Gestures** | Customizable swipes, pinches, and taps to open apps or trigger system actions. |
| **Personalization** | Hide unwanted apps, switch custom fonts, and pick from built-in minimal themes. |

#

## 📸 Screenshots

### 1. Three Core Modes
| Text-Only Detox | Normal Mode | Drawer Mode |
| :---: | :---: | :---: |
| <img src="docs/screenshots/text-mode.png" width="250" alt="Text Only Mode" /> | <img src="docs/screenshots/normal-mode.png" width="250" alt="Normal Mode" /> | <img src="docs/screenshots/drawer-mode.png" width="250" alt="Drawer Mode" /> |

### 2. Privacy & Secret App Cloning
| Secret Vault Access | Password Protection | Hidden Apps List |
| :---: | :---: | :---: |
| <img src="docs/screenshots/secret-gesture.png" width="250" alt="Secret Gesture Access" /> | <img src="docs/screenshots/password-screen.png" width="250" alt="Password Protection" /> | <img src="docs/screenshots/hidden-apps.png" width="250" alt="Hidden Apps" /> |

### 3. Focus Tools & Gestures
| App Lock Timer | Gesture Configuration | Theme & Font Customization |
| :---: | :---: | :---: |
| <img src="docs/screenshots/app-lock.png" width="250" alt="App Lock Timer" /> | <img src="docs/screenshots/gestures.png" width="250" alt="Gesture Customization" /> | <img src="docs/screenshots/themes.png" width="250" alt="Theme Customization" /> |

#

## 📥 Installation

1. Go to the [Releases Page](https://github.com/indraamani/ZenLauncher/releases) and download the latest `app-release.apk`.
2. Open the file on your device and install it (enable *"Install from Unknown Sources"* if prompted).
3. Open **Zen Launcher** and set it as your default home app.

#

## 🛠️ Android Build Process

If you want to compile **Zen Launcher** yourself directly from source, follow these steps:

### Building via Command Line (Gradle)

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/indraamani/ZenLauncher.git](https://github.com/indraamani/ZenLauncher.git)
   cd zen-launcher
   ```

2. **Build Debug APK:**
   ```bash
   ./gradlew assembleDebug
   ```
   *The compiled debug APK will be generated at:* `app/build/outputs/apk/debug/app-debug.apk`

3. **Build Unsigned Release APK:**
   ```bash
   ./gradlew assembleRelease
   ```
   *The release binary will be generated at:* `app/build/outputs/apk/release/app-release-unsigned.apk`

#

## 📜 License & Contribution Policy

This repository is open-source under the **GPL-3.0 License**.

### 🛑 Codebase & Forking Guidelines
* **Main Codebase Maintenance:** The primary repository code is maintained solely by the project owner.
* **Forks & Independent Builds:** You are completely free to **fork** this repository, modify it, and build your own separate version!
* **Attribution Required:** Any fork or derivative work **must retain visible credit** to the original author with a link back to this repository.

#

<p align="center">
  <b>Enjoying Zen Launcher? Leave a ⭐️ on GitHub to support the project!</b>
</p>