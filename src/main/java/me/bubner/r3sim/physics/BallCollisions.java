package me.bubner.r3sim.physics;

import me.bubner.r3sim.Util;
import me.bubner.r3sim.objects.Ball;

import java.util.List;

/**
 * Calculates ball-to-ball collisions.
 *
 * @author Lucas Bubner, 2025
 */
public class BallCollisions {
    public static void enable() {
        Util.DeltaTimer ballPhysics = new Util.DeltaTimer((dtSec) -> {
            List<Ball> balls = Solid.OBJECTS.stream()
                    .filter(o -> o instanceof Ball)
                    .map(b -> (Ball) b)
                    .toList();
            for (int i = 0; i < balls.size(); i++) {
                Ball ball = balls.get(i);
                // Only process combinations of balls, not permutations
                for (int j = i + 1; j < balls.size(); j++) {
                    Ball otherBall = balls.get(j);
                    if (!ball.isIntersecting(otherBall.getPosition())) continue;

                    // TODO: ball physics
                }
            }
        });
        ballPhysics.start();
    }
}
