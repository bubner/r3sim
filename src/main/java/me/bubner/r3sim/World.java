package me.bubner.r3sim;

import javafx.scene.Group;
import javafx.scene.Node;
import me.bubner.r3sim.geometry.Copyable;
import me.bubner.r3sim.geometry.Line;
import me.bubner.r3sim.geometry.Plane;
import me.bubner.r3sim.geometry.Point;
import me.bubner.r3sim.objects.Ball;
import me.bubner.r3sim.objects.XYPlane;
import me.bubner.r3sim.objects.XZPlane;
import me.bubner.r3sim.objects.YZPlane;
import me.bubner.r3sim.physics.RotatableAboutZ;
import me.bubner.r3sim.physics.Solid;

import static me.bubner.r3sim.Util.vec;

/**
 * Declaration of all meaningful objects that will be added into the world.
 *
 * @author Lucas Bubner, 2025
 */
public class World extends Group {
    public void init() {
        Node[] forwardBox = {
                new Point(vec(350, 300, 200)),
                new Point(vec(600, 300, 200)),
                new Point(vec(350, -300, 200)),
                new Point(vec(600, -300, 200)),
                new Point(vec(350, 300, -200)),
                new Point(vec(600, 300, -200)),
                new Point(vec(350, -300, -200)),
                new Point(vec(600, -300, -200)),
                new Plane(vec(350, 300, 200), vec(0, -1, 0), vec(1, 0, 0))
                        .setColorOpacity(0.4)
                        .render(0, 600, 0, 250),
                new Plane(vec(350, 300, -200), vec(0, -1, 0), vec(1, 0, 0))
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
                        .render(0, 600, 0, 400),
        };
        for (Node node : forwardBox) {
            double[] angles = {-Math.PI / 4, Math.PI / 4};
            for (double ang : angles) {
                RotatableAboutZ copied = (RotatableAboutZ) (((Copyable) node).copy());
                copied.rotateAboutZBy(ang);
                if (copied instanceof Solid s)
                    s.enablePhysicsInteractions();
                getChildren().add((Node) copied);
            }
        }
        getChildren().addAll(
                new XYPlane().render(),
                new XZPlane().render(),
                new YZPlane().render(),
                Util.apply(new Ball(vec(400, 200, -100))
                        .setAcceleration(vec(0, -10, -100))
                        .setVelocity(vec(200, -70, 110))
                        .setShowVelocityVector(true), b -> {
                    b.enablePhysicsInteractions();
                    b.rotateAboutZBy(Math.PI / 4);
                }),
                Util.apply(new Ball(vec(560, -250, 170))
                        .setAcceleration(vec(10, 0, -100))
                        .setVelocity(vec(50, -50, -20))
                        .setShowVelocityVector(true), b -> {
                    b.enablePhysicsInteractions();
                    b.rotateAboutZBy(Math.PI / 4);
                })
        );
        Ball bouncer = Util.apply(new Ball(vec(-200, -400, 100))
                .setAcceleration(vec(0, 0, -100))
                .setVelocity(vec(0, 0, 0))
                .setShowVelocityVector(true), b -> {
            b.enablePhysicsInteractions();
            b.rotateAboutZBy(Math.PI / 4);
        });
        getChildren().add(bouncer);
        for (int x = 0; x <= 400; x += 100) {
            Ball newBall = (Ball) bouncer.copy();
            newBall.setPosition(vec(-200 + x, -400, 100 - x / 4.0));
            newBall.velocity = vec(0, 0, -x / 4.0);
            newBall.rotateAboutZBy(Math.PI / 4);
            getChildren().add(newBall);
        }
    }
}