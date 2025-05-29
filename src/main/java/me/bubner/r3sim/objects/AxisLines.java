package me.bubner.r3sim.objects;

import javafx.scene.Group;
import javafx.scene.shape.Box;

public class AxisLines extends Group {
    public AxisLines() {
        Box box = new Box(100,10,10);
        getChildren().add(box);
    }
}
