package me.bubner.r3sim.camera;

import javafx.animation.AnimationTimer;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

/**
 * Displays yaw and tilt for the camera.
 *
 * @author Lucas Bubner, 2025
 */
public class AngleOverlay extends StackPane {
    public AngleOverlay() {
        Text hudText = new Text("(0.0°, 0.0°)");
        hudText.setFill(Color.WHITE);
        hudText.setFont(Font.font(16));

        AnimationTimer anglesDisplay = new AnimationTimer() {
            @Override
            public void handle(long now) {
                hudText.setText(String.format("(%.1f°, %.1f°)", MainCamera.getYaw(), MainCamera.getPitch()));
            }
        };
        anglesDisplay.start();

        getChildren().add(hudText);
    }
}
