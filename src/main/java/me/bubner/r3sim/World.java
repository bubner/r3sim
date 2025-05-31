package me.bubner.r3sim;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import me.bubner.r3sim.geometry.Line;
import me.bubner.r3sim.objects.XYPlane;
import me.bubner.r3sim.objects.XZPlane;
import me.bubner.r3sim.objects.YZPlane;

/**
 * Declaration of all meaningful objects that will be added into the world.
 *
 * @author Lucas Bubner, 2025
 */
public class World extends Group {
    public World() {
        getChildren().addAll(
                new XYPlane().render(),
                new XZPlane().render(),
                new YZPlane().render(),
                new Line(new Point3D(200,0,0), new Point3D(1, 1, 1)).render()
        );
    }
}