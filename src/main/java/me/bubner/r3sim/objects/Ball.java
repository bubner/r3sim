package me.bubner.r3sim.objects;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import me.bubner.r3sim.R3Sim;
import me.bubner.r3sim.Util;
import me.bubner.r3sim.geometry.Line;
import me.bubner.r3sim.geometry.Point;
import me.bubner.r3sim.physics.Solid;

import static me.bubner.r3sim.Util.vec;

/**
 * Point with three-dimensional physics.
 *
 * @author Lucas Bubner, 2025
 */
public class Ball extends Point implements Solid {
    private static final double BALL_RADIUS = 30;
    private static final Color BALL_COLOUR = Color.YELLOW;

    private final Line velocityVector = new Line(vec(0, 0, 0), vec(0, 0, 0));
    private boolean showVelocityVector;

    public Point3D velocity = vec(0, 0, 0);
    public Point3D acceleration = vec(0, 0, 0);

    public Ball(Point3D origin) {
        super(origin);
        setRadius(BALL_RADIUS);
        setMaterial(new PhongMaterial(BALL_COLOUR));

        velocityVector.setRadius(Line.LINE_WIDTH * 5);
        velocityVector.setMaterial(new PhongMaterial(Color.ORANGERED));
        R3Sim.getWorld().getChildren().add(velocityVector);

        Util.DeltaTimer physics = new Util.DeltaTimer((dtSec) -> {
            Point3D position = getPosition();
            if (showVelocityVector) {
                velocityVector.startPoint = position;
                // Velocity is relative so it is from startPoint, add a radius length to
                // the vector to account for the ball itself
                velocityVector.directionVector = Util.lerp(velocityVector.directionVector,
                        velocity.add(velocityVector.directionVector.normalize().multiply(getRadius())), dtSec * 5);
                velocityVector.render(0, 1);
            }
            velocity = velocity.add(acceleration.multiply(dtSec));
            // Planar collisions handled per ball
            for (Solid object : Solid.OBJECTS) {
                if (!object.isIntersecting(position) || object instanceof Ball)
                    continue;
                // Vector reflection formula d=2(d dot n)n
                Point3D normal = object.getNormalVector().normalize();
                velocity = velocity.subtract(normal.multiply(2 * velocity.dotProduct(normal)))
                        .multiply(object.getCollisionEnergyMultiplier());
            }
            setPosition(position.add(velocity.multiply(dtSec)));
        });

        physics.start();
    }

    public Ball setShowVelocityVector(boolean show) {
        showVelocityVector = show;
        return this;
    }

    public Ball setVelocity(Point3D velocity) {
        this.velocity = velocity;
        return this;
    }

    public Ball setAcceleration(Point3D acceleration) {
        this.acceleration = acceleration;
        return this;
    }

    @Override
    public Point3D getNormalVector() {
        return velocity;
    }

    @Override
    public double getCollisionEnergyMultiplier() {
        return 1;
    }

    @Override
    public boolean isIntersecting(Point3D query) {
        double distance = query.subtract(getPosition()).magnitude();
        // Too close, must be a self-reference
        if (distance <= 1e-8)
            return false;
        return distance <= 2 * getRadius();
    }
}
