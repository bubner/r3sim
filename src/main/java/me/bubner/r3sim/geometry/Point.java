package me.bubner.r3sim.geometry;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import me.bubner.r3sim.physics.RotatableAboutZ;

/**
 * A spherical coloured point rendered immediately.
 *
 * @author Lucas Bubner, 2025
 */
public class Point extends Sphere implements RotatableAboutZ {
    private static final Color POINT_COLOUR = Color.CYAN;
    private static final double POINT_SIZE = 5;

    public Point(Point3D point) {
        super(POINT_SIZE);
        setMaterial(new PhongMaterial(POINT_COLOUR));
        setPosition(point);
    }

    public Point3D getPosition() {
        return new Point3D(getTranslateX(), getTranslateY(), getTranslateZ());
    }

    public void setPosition(Point3D newPosition) {
        setTranslateX(newPosition.getX());
        setTranslateY(newPosition.getY());
        setTranslateZ(newPosition.getZ());
    }

    @Override
    public void rotateAboutZBy(double angRad) {
        // TODO
    }
}
