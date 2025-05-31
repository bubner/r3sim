package me.bubner.r3sim;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import me.bubner.r3sim.geometry.Line;
import me.bubner.r3sim.geometry.Plane;
import me.bubner.r3sim.geometry.Point;
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
                new Line(new Point3D(200, 100, -100), new Point3D(1, 0, 0))
                        .render(0, 200),
                new Line(new Point3D(400, 100, -100), new Point3D(0, 1, 0))
                        .render(-200, 0),
                new Line(new Point3D(400, -100, -100), new Point3D(1, 0, 0))
                        .render(-200, 0),
                new Plane(new Point3D(200, 100, -100), new Point3D(1, 0, 0), new Point3D(0, 1, 0))
                        .render(0, 200, -200, 0),
                new Line(new Point3D(400, 100, -100), new Point3D(0, 0, 1))
                        .render(0, 300),
                new Line(new Point3D(400, 100, 200), new Point3D(0, 1,0))
                        .render(-200, 0),
                new Line(new Point3D(400, -100, 200), new Point3D(0, 0, 1))
                        .render(-300, 0),
                new Plane(new Point3D(400, 100, -100), new Point3D(0, 1, 0), new Point3D(0, 0, 1))
                        .render(-200, 0, 0, 300),
                new Line(new Point3D(200, 100, -100), new Point3D(0, 0, 1))
                        .render(-300, 0),
                new Line(new Point3D(200, -100, -100), new Point3D(0, 0, 1))
                        .render(-300, 0),
                new Point(new Point3D(400, 100, -100)),
                new Point(new Point3D(400, -100, -100)),
                new Plane(new Point3D(200, -100, -400), new Point3D(0, 0, 1), new Point3D(200, 200, 300))
                        .render(0, 20, 0, 1),
                new Plane(new Point3D(200, 100, -400), new Point3D(0, 0, 1), new Point3D(200, -200, 300))
                        .render(0, 20, 0, 1),
                new Plane(new Point3D(400, -100, -100), new Point3D(0, 1, 0), new Point3D(100, 0, -300))
                        .render(0, 200, 0, 1)
        );
    }
}