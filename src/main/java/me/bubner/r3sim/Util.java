package me.bubner.r3sim;

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
}
