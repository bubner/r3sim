package me.bubner.r3sim.physics;

import javafx.geometry.Point3D;
import me.bubner.r3sim.Util;
import me.bubner.r3sim.objects.Ball;

import java.util.ArrayList;
import java.util.List;

/**
 * Calculates ball-to-ball collisions under constant mass.
 *
 * @author Lucas Bubner, 2025
 */
public class BallCollisions {
    private static final double BALL_INTERACTION_COOLDOWN_SEC = 3;

    public static void enable() {
        ArrayList<Ball> debouncedBalls = new ArrayList<>();
        double[] acc = {0};
        Util.DeltaTimer ballPhysics = new Util.DeltaTimer((dtSec) -> {
            acc[0] += dtSec;
            if (acc[0] >= BALL_INTERACTION_COOLDOWN_SEC) {
                debouncedBalls.clear();
                acc[0] = 0;
            }
            List<Ball> balls = Solid.OBJECTS.stream()
                    .filter(o -> o instanceof Ball)
                    .map(b -> (Ball) b)
                    .toList();
            for (int i = 0; i < balls.size(); i++) {
                Ball ball = balls.get(i);
                if (debouncedBalls.contains(ball))
                    continue;
                // Only process combinations of balls, not permutations
                for (int j = i + 1; j < balls.size(); j++) {
                    Ball otherBall = balls.get(j);
                    if (debouncedBalls.contains(otherBall))
                        continue;
                    if (!ball.isIntersecting(otherBall.getPosition())) continue;

                    // https://vanhunteradams.com/Pico/Galton/Collisions.html
                    // (r_m-r_M)/|r_m-r_M|, inertial frame from `ball`
                    Point3D direction = otherBall.getPosition().subtract(ball.getPosition()).normalize();
                    // Assumes mass of balls is constant, m=1, M=1 -> 2/2*(d dot (v_i-V_i))d
                    Point3D deltaV = direction.multiply(direction.dotProduct(otherBall.velocity.subtract(ball.velocity)));
                    ball.velocity = deltaV;
                    // dv = -dV since mass is constant
                    otherBall.velocity = deltaV.multiply(-1);

                    // Only fire collisions for these two balls once
                    debouncedBalls.add(ball);
                    debouncedBalls.add(otherBall);
                }
            }
        });
        ballPhysics.start();
    }
}
