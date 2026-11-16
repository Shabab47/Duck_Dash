package edu.bauet.java.cse.duckrun;

import edu.bauet.java.cse.duckrun.scenes.StoryScene;
import edu.bauet.java.cse.duckrun.utils.AssetLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;

public class MainApp extends Application {

    public static final int WINDOW_WIDTH = 1280;
    public static final int WINDOW_HEIGHT = 720;
    private static Stage primaryStage;
    private static double lastWindowedWidth = 899;
    private static double lastWindowedHeight = 536;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        primaryStage.setTitle("Duck Dash");
        primaryStage.setResizable(true);
        primaryStage.setMinWidth(320);
        primaryStage.setMinHeight(180);

        System.setProperty(
                "com.sun.media.jfxmediaimpl.platform.gstreamer.GSTPlatform.DISABLE_AV_SYNC",
                "true"
        );

        AssetLoader.preloadAssets();
        AssetLoader.preloadVideos();
        edu.bauet.java.cse.duckrun.utils.MusicManager.getInstance().preloadSfx(
                "/audio/sound_effect/stepMAX.mp3",
                "/audio/sound_effect/step.mp3",
                "/audio/sound_effect/step3x.mp3",
                "/audio/sound_effect/hit.mp3");

        StoryScene storyScene = new StoryScene();
        Scene scene = storyScene.createScene(stage);

        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setFullScreenExitHint("");
        primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        primaryStage.setFullScreen(true);
    }

    public static void switchScene(Scene scene) {
        boolean wasFullScreen = primaryStage != null && primaryStage.isFullScreen();
        primaryStage.setScene(scene);
        if (wasFullScreen) {
            javafx.application.Platform.runLater(() -> {
                if (primaryStage != null && !primaryStage.isFullScreen()) {
                    primaryStage.setFullScreen(true);
                }
            });
        }
    }

    public static Stage getStage() {
        return primaryStage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    /** Current uniform scale factor relative to the design resolution. */
    public static double getScaleFactor() {
        if (primaryStage == null) return 1.0;
        double scaleX = primaryStage.getWidth()  / WINDOW_WIDTH;
        double scaleY = primaryStage.getHeight() / WINDOW_HEIGHT;
        return Math.min(scaleX, scaleY);
    }

    public static void rememberWindowedSize() {
        if (primaryStage != null && !primaryStage.isFullScreen()) {
            double w = primaryStage.getWidth();
            double h = primaryStage.getHeight();
            if (w > 0 && h > 0) {
                lastWindowedWidth = w;
                lastWindowedHeight = h;
            }
        }
    }

    public static double getLastWindowedWidth() {
        return lastWindowedWidth;
    }

    public static double getLastWindowedHeight() {
        return lastWindowedHeight;
    }

    public static void main(String[] args) {
        launch(args);
    }
}