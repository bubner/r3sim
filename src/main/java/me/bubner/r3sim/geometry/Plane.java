package me.bubner.r3sim.geometry;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import me.bubner.r3sim.camera.MainCamera;

/**
 * Vector parameterised form of a plane.
 * 
 * @author Lucas Bubner, 2025
 */
public class Plane extends Group {
    private static final double PLANE_DEPTH = 1;
    
    private final Point3D startPoint;
    private final Point3D basis1;
    private final Point3D basis2;
    
    public Plane(Point3D A, Point3D AV, Point3D AW) {
        startPoint = A;
        basis1 = AV;
        basis2 = AW;
    }
    
    public Plane render(double lambdaMin, double lambdaMax, double muMin, double muMax) {
        // TODO
        return this;
    }
    
    public Plane render() {
        return render(-MainCamera.CAMERA_FAR_CLIP, MainCamera.CAMERA_FAR_CLIP, -MainCamera.CAMERA_FAR_CLIP, MainCamera.CAMERA_FAR_CLIP);
    }
}
