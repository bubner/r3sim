package me.bubner.r3sim.objects;

import javafx.scene.Group;
import javafx.scene.shape.Box;

public class FloorPlane extends Group {
    public FloorPlane() {
        Box box = new Box(10000, 10000, 1);
        box.setTranslateZ(-500);
        getChildren().addAll(box);
    }
}
