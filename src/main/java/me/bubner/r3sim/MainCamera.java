package me.bubner.r3sim;

import javafx.scene.PerspectiveCamera;
import javafx.scene.transform.Rotate;

public class MainCamera extends PerspectiveCamera {
    public MainCamera() {
        super(false);
        getTransforms().addAll(new Rotate(180, Rotate.Z_AXIS));
    }
}