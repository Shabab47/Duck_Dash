# How to Install and Play Duck Dash

## Option 1: Install from a release (recommended for players)

Download the release `.zip` for your platform from the **Releases** page: https://github.com/Shabab47/Duck_Dash/releases

Each release zip includes the installer and this `INSTALL.md` file.

### Windows (`.zip` containing `.exe` installer)
1. Download **`DuckDash-<version>-windows.zip`** from the latest release.
2. Extract the zip to a folder (e.g. `Downloads` or `Desktop`).
3. Run **`DuckDash-<version>-windows-installer.exe`**.
   - If Windows SmartScreen appears, click **More info** → **Run anyway** (only if you trust the source).
   - If needed, right-click the installer → **Run as administrator**.
4. In the installer, you can **browse and choose where to install** Duck Dash.
5. Complete the installation.
   - A **desktop shortcut** will be created (and a Start Menu entry, when available).
6. **Run the game from:**
   - The **Duck Dash desktop shortcut**, or
   - The **Start Menu** entry, or
   - The install folder you selected (run `DuckDash.exe` inside that folder).

### Linux (`.zip` containing `.deb` package, Debian/Ubuntu-based)
1. Download **`DuckDash-<version>-linux.zip`** from the latest release.
2. Extract the zip to a folder.
3. Install **`DuckDash-<version>-linux-amd64.deb`** using your package manager, for example:
   ```bash
   sudo apt install ./DuckDash-<version>-linux-amd64.deb
   ```
   (Run the command from the extracted folder, or use the full path to the `.deb` file.)
4. **Run the game from:**
   - Your **applications menu** (a launcher/shortcut is installed), or
   - The installed application entry for **DuckDash** provided by your desktop environment.

> Note: Actual shortcut/menu behavior can depend on your OS and desktop environment settings. If a shortcut is not created automatically, launch Duck Dash from the install location/applications menu.

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
