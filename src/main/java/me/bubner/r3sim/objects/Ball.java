package me.bubner.r3sim.objects;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import me.bubner.r3sim.R3Sim;
import me.bubner.r3sim.Util;
import me.bubner.r3sim.geometry.Line;
import me.bubner.r3sim.geometry.Point;
import me.bubner.r3sim.physics.Solid;

import java.util.HashSet;

import static me.bubner.r3sim.Util.vec;

/**
 * Point with three-dimensional physics.
 *
 * @author Lucas Bubner, 2025
 */
public class Ball extends Point implements Solid {
    private static final double BALL_RADIUS = 30;
    private static final Color BALL_COLOUR = Color.YELLOW;
    private static final double COEFFICIENT_OF_RESTITUTION = 1;
    private static final double SAME_PLANE_INTERACTION_COOLDOWN_SEC = 0.2;

    private final Line velocityVector = new Line(vec(0, 0, 0), vec(0, 0, 0));
    public volatile Point3D velocity = vec(0, 0, 0);
    public volatile Point3D acceleration = vec(0, 0, 0);
    private boolean showVelocityVector;

    public Ball(Point3D origin) {
        super(origin);
        setRadius(BALL_RADIUS);
        setMaterial(new PhongMaterial(BALL_COLOUR));

        velocityVector.setRadius(Line.LINE_WIDTH * 5);
        velocityVector.setMaterial(new PhongMaterial(Color.ORANGERED));
        R3Sim.getWorld().getChildren().add(velocityVector);

        HashSet<Solid> debouncedObjects = new HashSet<>();
        double[] acc = {0};
        Util.DeltaTimer physics = new Util.DeltaTimer((dtSec) -> {
            acc[0] += dtSec;
            if (acc[0] >= SAME_PLANE_INTERACTION_COOLDOWN_SEC) {
                debouncedObjects.clear();
                acc[0] = 0;
            }
            Point3D position = getPosition();
            if (showVelocityVector) {
                velocityVector.startPoint = position;
                // Velocity is relative so it is from startPoint, add a radius length to
                // the vector to account for the ball itself
                velocityVector.directionVector = Util.lerp(velocityVector.directionVector,
                        velocity.add(velocityVector.directionVector.normalize().multiply(getRadius())), dtSec * 5);
                velocityVector.render(0, 1);
            }
            Point3D newVelocity = velocity.add(acceleration.multiply(dtSec));
            // Planar collisions handled per ball
            for (Solid object : Solid.OBJECTS) {
                if (object instanceof Ball || debouncedObjects.contains(object) || !object.isIntersecting(position))
                    continue;
                // Simplified derivation of https://vanhunteradams.com/Pico/Galton/Collisions.html
                // for bouncing off a wall with coefficient of restitution
                Point3D normal = object.getNormalVector().normalize();
                newVelocity = velocity.subtract(
                        // delta V
                        normal.multiply((1 + object.getRestitutionCoefficient()) * velocity.dotProduct(normal))
                );
                // Prevent multiple plane collisions for a single event
                debouncedObjects.add(object);
            }
            velocity = newVelocity;
            setPosition(position.add(newVelocity.multiply(dtSec)));
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
    public double getRestitutionCoefficient() {
        return COEFFICIENT_OF_RESTITUTION;
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
