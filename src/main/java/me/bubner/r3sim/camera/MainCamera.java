package me.bubner.r3sim.camera;

import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.transform.Rotate;
import me.bubner.r3sim.R3Sim;
import me.bubner.r3sim.Util;

import java.util.HashSet;
import java.util.Set;

import static javafx.scene.input.KeyCode.*;

/**
 * Camera controller centred at (0,0,0) and rotatable with the arrow keys.
 * <p>
 * Coordinates follow +x forward, +y left, +z up, +theta ccw
 *
 * @author Lucas Bubner, 2025
 */
public class MainCamera extends Group {
    public static final double CAMERA_FAR_CLIP = 10000.0;
    private static final double CAMERA_NEAR_CLIP = 0.0;
    private static final double CAMERA_FOV = 75;
    private static final double INPUT_DEGREES_PER_SECOND_YAW = 60.0;
    private static final double INPUT_DEGREES_PER_SECOND_PITCH = 30.0;
    private static double yaw = 0, pitch = 0;
    private final PerspectiveCamera camera;
    private final Group yawGroup;
    private final Group pitchGroup;

    public MainCamera() {
        // Remap from +x right, +y down, +z forward to +x forward, +y left, +z up
        getTransforms().addAll(
                new Rotate(-90, Rotate.Z_AXIS),
                // Uses original axes, not transformed axes which instead would be +90 in Y
                new Rotate(-90, Rotate.X_AXIS)
        );
        camera = new PerspectiveCamera(true);
        camera.setFieldOfView(CAMERA_FOV);
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);

        // Actual camera object is controlled by two independent angle controllers
        pitchGroup = new Group(camera);
        yawGroup = new Group(pitchGroup);
        getChildren().add(yawGroup);

        Scene scene = R3Sim.getMainScene();
        assert scene != null;

        // Track held keys
        Set<KeyCode> pressed = new HashSet<>();
        scene.setOnKeyPressed(e -> pressed.add(e.getCode()));
        scene.setOnKeyReleased(e -> pressed.remove(e.getCode()));

        Util.DeltaTimer inputHandler = new Util.DeltaTimer((dtSec) -> {
            // Use a delta time to control rotation speed (and smoothness)
            double deltaYaw = INPUT_DEGREES_PER_SECOND_YAW * dtSec;
            double deltaPitch = INPUT_DEGREES_PER_SECOND_PITCH * dtSec;
            if (pressed.contains(UP)) {
                pitch += deltaPitch;
                pitch = Util.clamp(pitch, -90, 90);
                pitchGroup.setRotationAxis(Rotate.X_AXIS);
                pitchGroup.setRotate(pitch);
            }
            if (pressed.contains(DOWN)) {
                pitch -= deltaPitch;
                pitch = Util.clamp(pitch, -90, 90);
                pitchGroup.setRotationAxis(Rotate.X_AXIS);
                pitchGroup.setRotate(pitch);
            }
            if (pressed.contains(LEFT)) {
                yaw += deltaYaw;
                yawGroup.setRotationAxis(Rotate.Y_AXIS);
                // Relative to the world this is actually incorrect, but we try to keep the reference frame +theta ccw
                yawGroup.setRotate(-yaw);
            }
            if (pressed.contains(RIGHT)) {
                yaw -= deltaYaw;
                yawGroup.setRotationAxis(Rotate.Y_AXIS);
                yawGroup.setRotate(-yaw);
            }
            yaw = Util.wrap(yaw, -180, 180);
        });

        // Run permanently in the background
        inputHandler.start();
    }

    public static double getYaw() {
        return yaw;
    }

    public static double getPitch() {
        return pitch;
    }

    public Camera getCamera() {
        return camera;
    }
}
