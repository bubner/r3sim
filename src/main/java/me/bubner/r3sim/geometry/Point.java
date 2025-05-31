package me.bubner.r3sim.geometry;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;

/**
 * A spherical coloured point rendered immediately.
 *
 * @author Lucas Bubner, 2025
 */
public class Point extends Sphere {
    private static final Color POINT_COLOUR = Color.CYAN;
    private static final double POINT_SIZE = 5;

    public Point(Point3D point) {
        super(POINT_SIZE);
        setTranslateX(point.getX());
        setTranslateY(point.getY());
        setTranslateZ(point.getZ());
        setMaterial(new PhongMaterial(POINT_COLOUR));
    }
}
