package me.bubner.r3sim;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import me.bubner.r3sim.geometry.Line;
import me.bubner.r3sim.objects.FloorPlane;

/**
 * Declaration of all meaningful objects that will be added into the world.
 *
 * @author Lucas Bubner, 2025
 */
public class World extends Group {
    public World() {
        getChildren().addAll(
                new FloorPlane(),
                new Line(new Point3D(500, 0, 0), new Point3D(1, 1, 1))
        );
    }
}