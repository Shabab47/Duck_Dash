# How to Install and Play Duck Dash

## Option 1: Install from a release (recommended for players)

### Windows (`.exe` installer)
1. Go to the project **Releases** page: https://github.com/Shabab47/Duck_Dash/releases
2. Open the latest release and download the Windows installer asset:
   - `DuckDash-<version>-windows-installer.exe` (newer releases)
   - or the `.exe` file attached to the release
3. Run the downloaded `.exe` file.
   - If Windows SmartScreen appears, click **More info** → **Run anyway** (only if you trust the source).
   - If needed, right-click the installer → **Run as administrator**.
4. Follow the installer steps to complete installation.
5. Launch **Duck Dash** from the Start Menu / desktop shortcut (or from the install folder).

### Linux (`.deb` package, Debian/Ubuntu-based)
1. Go to the project **Releases** page: https://github.com/Shabab47/Duck_Dash/releases
2. Download the Linux package asset:
   - `DuckDash-<version>-linux-amd64.deb` (newer releases)
   - or the `.deb` file attached to the release
3. Install it using your package manager, for example:
   ```bash
   sudo apt install ./DuckDash-<version>-linux-amd64.deb
   ```
   (Replace the filename with the file you downloaded.)
4. Launch **Duck Dash** from your applications menu.

> Note: Release asset names may vary slightly in older releases. Download the `.exe` for Windows or `.deb` for Linux from the release assets.

## Option 2: Run from source (developers)

### Prerequisites
- **JDK 21** (or compatible JDK version required by the project)
- **Maven 3.8+**
- Git (to clone the repository)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/Shabab47/Duck_Dash.git
   cd Duck_Dash
   ```
2. Run the game:
   ```bash
   mvn javafx:run
   ```

(You can also build with `mvn clean package`.)

## How to Play
- **Jump:** `Space` / `Up Arrow` / `W`
- **Crouch:** `Down Arrow` / `S`
- **Pause:** `Esc` (or the on-screen pause button)
- **Menu navigation:** Mouse click

## Troubleshooting
- **Windows installer blocked:** If SmartScreen blocks the installer, choose **More info** → **Run anyway** (only if you trust the release). You can also check the file’s Properties for an **Unblock** option.
- **App installed but won’t start:** Try launching it from the install folder directly. If it still fails, re-download the release asset and reinstall.
- **Running from source fails:** Verify `java -version` and `mvn -version`, ensure JDK/Maven are installed correctly, and run `mvn clean javafx:run` from the project root.
