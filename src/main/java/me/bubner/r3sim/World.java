package me.bubner.r3sim;

import javafx.scene.Group;
import me.bubner.r3sim.geometry.Line;
import me.bubner.r3sim.geometry.Plane;
import me.bubner.r3sim.geometry.Point;
import me.bubner.r3sim.objects.XYPlane;
import me.bubner.r3sim.objects.XZPlane;
import me.bubner.r3sim.objects.YZPlane;

import static me.bubner.r3sim.Util.vec;

/**
 * Declaration of all meaningful objects that will be added into the world.
 *
 * @author Lucas Bubner, 2025
 */
public class World extends Group {
    public World() {
        Point[] box = {
                new Point(vec(350, 300, 200)),
                new Point(vec(600, 300, 200)),
                new Point(vec(350, -300, 200)),
                new Point(vec(600, -300, 200)),
                new Point(vec(350, 300, -200)),
                new Point(vec(600, 300, -200)),
                new Point(vec(350, -300, -200)),
                new Point(vec(600, -300, -200))
        };
        // TODO: rotation and replication around z
        getChildren().addAll(box);
        getChildren().addAll(
                new XYPlane().render(),
                new XZPlane().render(),
                new YZPlane().render(),
                new Plane(vec(350, 300, 200), vec(0, -1, 0), vec(1, 0, 0))
                        .setColorOpacity(0.4)
                        .render(0, 600, 0, 250),
                new Plane(vec(350, 300, -200), vec(0, -1, 0), vec(1,0,0))
                        .setColorOpacity(0.4)
                        .render(0, 600, 0, 250),
                new Plane(vec(600, 300, -200), vec(0, -1, 0), vec(0, 0, 1))
                        .render(0, 600, 0, 400),
                new Plane(vec(600, 300, -200), vec(-1, 0, 0), vec(0, 0, 1))
                        .setColorOpacity(0.3)
                        .render(0, 250, 0, 400),
                new Plane(vec(350, -300, -200), vec(1, 0, 0), vec(0, 0, 1))
                        .setColorOpacity(0.3)
                        .render(0, 250, 0, 400),
                new Line(vec(600, -300, 200), vec(0, 1, 0))
                        .render(0, 600),
                new Line(vec(350, -300, 200), vec(0, 1, 0))
                        .render(0, 600),
                new Line(vec(600, 300, -200), vec(0, -1, 0))
                        .render(0, 600),
                new Line(vec(350, 300, -200), vec(0, -1, 0))
                        .render(0, 600),
                new Line(vec(600, -300, -200), vec(-250, 0, 400))
                        .render(0, 1),
                new Line(vec(600, 300, 200), vec(-250, 0, -400))
                        .render(0, 1),
                new Plane(vec(350, -300, -200), vec(0, 1, 0), vec(0, 0, 1))
                        .setColorOpacity(0.05)
                        .render(0, 600, 0, 400)
        );
    }
}