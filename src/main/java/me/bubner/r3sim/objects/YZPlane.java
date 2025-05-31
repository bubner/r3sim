package me.bubner.r3sim.objects;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import me.bubner.r3sim.geometry.Plane;

public class YZPlane extends Plane {
    public YZPlane() {
        // Need to bump X a very tiny amount since straight through the camera will cause rendering bugs
        super(Point3D.ZERO.subtract(0.01, 0, 0), Rotate.Y_AXIS, Rotate.Z_AXIS);
        setColorOpacity(0.05);
    }
}
