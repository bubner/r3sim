package me.bubner.r3sim.geometry;

import javafx.geometry.Point3D;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Cylinder;
import javafx.scene.transform.Rotate;
import me.bubner.r3sim.Util;
import me.bubner.r3sim.camera.MainCamera;
import me.bubner.r3sim.physics.RotatableAboutZ;

/**
 * Vector parameterised form of a line.
 *
 * @author Lucas Bubner, 2025
 */
public class Line extends Cylinder implements RotatableAboutZ, Copyable {
    public static final double LINE_WIDTH = 1;
    private static final Color LINE_COLOUR = Color.RED;

    public Point3D startPoint;
    public Point3D directionVector;

    private double lambdaMin, lambdaMax;

    public Line(Point3D startPoint, Point3D directionVector) {
        // Height will be set later
        super(LINE_WIDTH, 0);
        setVisible(false);
        this.startPoint = startPoint;
        this.directionVector = directionVector;
        setMaterial(new PhongMaterial(LINE_COLOUR));
    }

    public Line render(double lambdaMin, double lambdaMax) {
        this.lambdaMin = lambdaMin;
        this.lambdaMax = lambdaMax;

        setVisible(true);
        Point3D p1 = startPoint.add(directionVector.multiply(lambdaMin));
        Point3D p2 = startPoint.add(directionVector.multiply(lambdaMax));

        // Length of cylinder is controlled by height so use the magnitude of the start and end points
        setHeight(p1.distance(p2));

        // Cylinder position is based from the center so set the cylinder to the middle between the points
        Point3D midpoint = p1.midpoint(p2);
        setTranslateX(midpoint.getX());
        setTranslateY(midpoint.getY());
        setTranslateZ(midpoint.getZ());

        getTransforms().clear();
        Rotate rotation = new Rotate(
                // Default axis of the cylinder is the Y axis, so use the angle between two vectors (on two unit vectors)
                // to calculate how much rotation is needed
                Math.toDegrees(Math.acos(directionVector.normalize().multiply(-1).dotProduct(Rotate.Y_AXIS))),
                // Then determine the axis to rotate about, which will be perpendicular to the default axis and direction vector
                // so we use the cross product
                directionVector.crossProduct(Rotate.Y_AXIS)
        );
        getTransforms().add(rotation);

        return this;
    }

    public Line render() {
        return render(-MainCamera.CAMERA_FAR_CLIP, MainCamera.CAMERA_FAR_CLIP);
    }

    @Override
    public void rotateAboutZBy(double angRad) {
        startPoint = Util.rotateAboutZ(startPoint, angRad);
        directionVector = Util.rotateAboutZ(directionVector, angRad);
        if (lambdaMax != 0 || lambdaMin != 0)
            render(lambdaMin, lambdaMax);
    }

    @Override
    public Node copy() {
        Line line = new Line(startPoint, directionVector);
        line.setHeight(getHeight());
        line.setVisible(isVisible());
        line.setMaterial(getMaterial());
        line.setTranslateX(getTranslateX());
        line.setTranslateY(getTranslateY());
        line.setTranslateZ(getTranslateZ());
        line.getTransforms().addAll(getTransforms());
        if (lambdaMax != 0 || lambdaMin != 0)
            line.render(lambdaMin, lambdaMax);
        return line;
    }
}
