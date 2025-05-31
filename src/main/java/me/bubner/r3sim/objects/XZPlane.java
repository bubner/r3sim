package me.bubner.r3sim.objects;

import javafx.geometry.Point3D;
import javafx.scene.transform.Rotate;
import me.bubner.r3sim.geometry.Plane;

public class XZPlane extends Plane {
    public XZPlane() {
        // Need to bump Y a very tiny amount since straight through the camera will cause rendering bugs
        super(Point3D.ZERO.subtract(0, 0.01, 0), Rotate.X_AXIS, Rotate.Z_AXIS);
        setColorOpacity(0.1);
    }
}
