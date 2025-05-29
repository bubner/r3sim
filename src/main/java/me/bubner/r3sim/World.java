package me.bubner.r3sim;

import javafx.scene.Group;
import me.bubner.r3sim.objects.AxisLines;

public class World extends Group {
    public World() {
        getChildren().addAll(new AxisLines());
    }
}