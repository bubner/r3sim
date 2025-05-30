package me.bubner.r3sim;

import javafx.scene.Group;
import me.bubner.r3sim.objects.FloorPlane;

/**
 * Declaration of all meaningful objects that will be added into the world.
 *
 * @author Lucas Bubner, 2025
 */
public class World extends Group {
    public World() {
        getChildren().addAll(
                new FloorPlane()
        );
    }
}