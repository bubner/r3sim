package me.bubner.r3sim.physics;

import javafx.geometry.Point3D;

import java.util.ArrayList;

/**
 * Declares a physics solid in the world.
 * 
 * @author Lucas Bubner, 2025
 */
public interface Solid {
    ArrayList<Solid> OBJECTS = new ArrayList<>();
    
    default void enablePhysicsInteractions() {
        OBJECTS.add(this);
    }
    
    Point3D getNormalVector();
    double getCollisionEnergyMultiplier();
    boolean isIntersecting(Point3D query);
}
