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
    private static final double POINT_SIZE = Line.LINE_WIDTH;
    
    public Point(Point3D point, Color color) {
        super(POINT_SIZE);
        setTranslateX(point.getX());
        setTranslateY(point.getY());
        setTranslateZ(point.getZ());
        setMaterial(new PhongMaterial(color));
    }
}
