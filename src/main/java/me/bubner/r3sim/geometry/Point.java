package me.bubner.r3sim.geometry;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import me.bubner.r3sim.physics.RotatableAboutZ;

import static me.bubner.r3sim.Util.vec;

/**
 * A spherical coloured point rendered immediately.
 *
 * @author Lucas Bubner, 2025
 */
public class Point extends Sphere implements RotatableAboutZ, Copyable {
    private static final Color POINT_COLOUR = Color.CYAN;
    private static final double POINT_SIZE = 5;

    public Point(Point3D point) {
        super(POINT_SIZE);
        setMaterial(new PhongMaterial(POINT_COLOUR));
        setPosition(point);
    }

    public Point3D getPosition() {
        return vec(getTranslateX(), getTranslateY(), getTranslateZ());
    }

    public void setPosition(Point3D newPosition) {
        setTranslateX(newPosition.getX());
        setTranslateY(newPosition.getY());
        setTranslateZ(newPosition.getZ());
    }

    @Override
    public void rotateAboutZBy(double angRad) {
        // \begin{bmatrix}\cos\theta&-\sin\theta&0\\\sin\theta&\cos\theta&0\\0&0&1\end{bmatrix}\begin{bmatrix}x\\y\\z\end{bmatrix}=\begin{bmatrix}x\cos\theta-y\sin\theta\\x\sin\theta+y\cos\theta\\z\end{bmatrix}
        double x = getTranslateX();
        double y = getTranslateY();
        // xcos(t)-ysin(t)
        setTranslateX(x * Math.cos(angRad) - y * Math.sin(angRad));
        setTranslateY(x * Math.sin(angRad) + y * Math.cos(angRad));
        // xsin(t)+ycos(t)
        // z=z
    }

    @Override
    public Node copy() {
        Point point = new Point(getPosition());
        point.setMaterial(getMaterial());
        point.setRadius(getRadius());
        return point;
    }
}
