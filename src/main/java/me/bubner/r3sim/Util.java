package me.bubner.r3sim;

import javafx.animation.AnimationTimer;
import javafx.geometry.Point3D;

import java.util.function.Consumer;

/**
 * Common utils.
 *
 * @author Lucas Bubner, 2025
 */
public class Util {
    public static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public static double wrap(double value, double min, double max) {
        double range = max - min;
        return ((value - min) % range + range) % range + min;
    }

    public static <T> T apply(T obj, Consumer<T> func) {
        func.accept(obj);
        return obj;
    }

    public static Point3D vec(double x, double y, double z) {
        return new Point3D(x, y, z);
    }

    public static Point3D lerp(Point3D a, Point3D b, double t) {
        return b.multiply(t).add(a.multiply(1 - Util.clamp(t, 0, 1)));
    }

    public static Point3D rotateAboutZ(Point3D point, double angRad) {
        double sx = point.getX();
        double sy = point.getY();
        return vec(sx * Math.cos(angRad) - sy * Math.sin(angRad),
                sx * Math.sin(angRad) + sy * Math.cos(angRad), point.getZ());
    }

    public static class DeltaTimer extends AnimationTimer {
        private final Consumer<Double> dtSec;
        private long lastTime = 0;

        public DeltaTimer(Consumer<Double> dtSec) {
            this.dtSec = dtSec;
        }

        @Override
        public void handle(long now) {
            if (lastTime == 0) {
                lastTime = now;
                return;
            }
            dtSec.accept((now - lastTime) / 1e9);
            lastTime = now;
        }
    }
}
