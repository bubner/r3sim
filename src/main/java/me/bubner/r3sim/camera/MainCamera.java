package me.bubner.r3sim.camera;

import javafx.animation.AnimationTimer;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import me.bubner.r3sim.R3Sim;

import java.util.HashSet;
import java.util.Set;

import static javafx.scene.input.KeyCode.*;

/**
 * Camera controller centred at (0,0,0) and rotatable with the arrow keys.
 * 
 * @author Lucas Bubner, 2025
 */
public class MainCamera extends PerspectiveCamera {
    public static double yaw = 0, pitch = 0;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double INPUT_DEGREES_PER_SECOND = 45.0;
    
    public MainCamera() {
        super(false);
        setNearClip(CAMERA_NEAR_CLIP);
        setFarClip(CAMERA_FAR_CLIP);
        
        Scene scene = R3Sim.getScene();
        assert scene != null;

        // Doesn't appear to be any held listeners so we track key state
        Set<KeyCode> pressed = new HashSet<>();
        scene.setOnKeyPressed(e -> pressed.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressed.remove(e.getCode()));
        
        AnimationTimer inputHandler = new AnimationTimer() {
            private long lastTime = 0;
            @Override
            public void handle(long now) {
                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double dtSec = (now - lastTime) / 1e9;
                lastTime = now;
                // Use a delta time to control rotation speed (and smoothness)
                double angle = INPUT_DEGREES_PER_SECOND * dtSec;
                // Pivot around the camera's current position to fixed rotate
                double x = getTranslateX();
                double y = getTranslateY();
                double z = getTranslateZ();
                if (pressed.contains(UP)) {
                    pitch += angle;
                    getTransforms().add(new Rotate(angle, x, y, z, Rotate.X_AXIS));
                }
                if (pressed.contains(DOWN)) {
                    pitch -= angle;
                    getTransforms().add(new Rotate(-angle, x, y, z, Rotate.X_AXIS));
                }
                if (pressed.contains(LEFT)) {
                    yaw -= angle;
                    getTransforms().add(new Rotate(-angle, x, y, z, Rotate.Y_AXIS));
                }
                if (pressed.contains(RIGHT)) {
                    yaw += angle;
                    getTransforms().add(new Rotate(angle, x, y, z, Rotate.Y_AXIS));
                }
                yaw = (yaw % 360 + 360) % 360;
                pitch = (pitch % 360 + 360) % 360;
            }
        };

        // Run permanently in the background
        inputHandler.start();
    }
}