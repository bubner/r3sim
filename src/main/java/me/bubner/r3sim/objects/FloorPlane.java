package me.bubner.r3sim.objects;

import javafx.scene.Group;
import javafx.scene.shape.Box;
import me.bubner.r3sim.camera.MainCamera;

public class FloorPlane extends Group {
    public FloorPlane() {
        Box box = new Box(MainCamera.CAMERA_FAR_CLIP, MainCamera.CAMERA_FAR_CLIP, 1);
        box.setTranslateZ(-500);
        getChildren().addAll(box);
    }
}
