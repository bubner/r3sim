package me.bubner.r3sim.geometry;

import javafx.geometry.Point3D;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import me.bubner.r3sim.Util;

/**
 * Point with vertical physics.
 * 
 * @author Lucas Bubner, 2025
 */
public class Ball extends Point {
    private static final double BALL_RADIUS = 30;
    private static final Color BALL_COLOUR = Color.YELLOW;
    
    private double velocity = 0;
    private double acceleration = 0;
    
    public Ball(Point3D origin) {
        super(origin);
        setRadius(BALL_RADIUS);
        setMaterial(new PhongMaterial(BALL_COLOUR));

        Util.DeltaTimer physics = new Util.DeltaTimer((dtSec) -> {
            velocity += acceleration * dtSec;
            setPosition(getPosition().add(0, 0, velocity * dtSec));
        });
        
        physics.start();
    }
    
    public Ball setAcceleration(double acceleration) {
        this.acceleration = acceleration;
        return this;
    }
}
