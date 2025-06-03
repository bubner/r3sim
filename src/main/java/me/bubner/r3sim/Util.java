package me.bubner.r3sim;

import javafx.animation.AnimationTimer;

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
