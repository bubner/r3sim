package me.bubner.r3sim.geometry;

import javafx.geometry.Point3D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.CullFace;
import javafx.scene.shape.DrawMode;
import javafx.scene.shape.MeshView;
import javafx.scene.shape.TriangleMesh;
import me.bubner.r3sim.Util;
import me.bubner.r3sim.camera.MainCamera;
import me.bubner.r3sim.physics.RotatableAboutZ;
import me.bubner.r3sim.physics.Solid;

/**
 * Vector parameterised form of a plane.
 *
 * @author Lucas Bubner, 2025
 */
public class Plane extends Group implements Solid, RotatableAboutZ, Copyable {
    public static final double PLANE_INTERACTION_EPSILON = 20;

    private Point3D startPoint;
    private Point3D basis1;
    private Point3D basis2;
    private Point3D normal;
    private double cartesianConstant;

    private double lambdaMin, lambdaMax, muMin, muMax;
    private double opacity = 0.5;
    private double restitution = 1;

    public Plane(Point A, Point3D AV, Point3D AW) {
        this(A.getPosition(), AV, AW);
    }

    public Plane(Point3D A, Point3D AV, Point3D AW) {
        setVisible(false);
        startPoint = A;
        basis1 = AV;
        basis2 = AW;
        normal = AV.crossProduct(AW);
        cartesianConstant = normal.dotProduct(A);
    }

    public Plane render(double lambdaMin, double lambdaMax, double muMin, double muMax) {
        this.lambdaMin = lambdaMin;
        this.lambdaMax = lambdaMax;
        this.muMin = muMin;
        this.muMax = muMax;

        setVisible(true);
        getChildren().clear();
        TriangleMesh mesh = new TriangleMesh();

        Point3D p00 = startPoint.add(basis1.multiply(lambdaMin)).add(basis2.multiply(muMin));
        Point3D p01 = startPoint.add(basis1.multiply(lambdaMin)).add(basis2.multiply(muMax));
        Point3D p10 = startPoint.add(basis1.multiply(lambdaMax)).add(basis2.multiply(muMin));
        Point3D p11 = startPoint.add(basis1.multiply(lambdaMax)).add(basis2.multiply(muMax));

        mesh.getPoints().addAll(
                (float) p00.getX(), (float) p00.getY(), (float) p00.getZ(),
                (float) p01.getX(), (float) p01.getY(), (float) p01.getZ(),
                (float) p10.getX(), (float) p10.getY(), (float) p10.getZ(),
                (float) p11.getX(), (float) p11.getY(), (float) p11.getZ()
        );
        mesh.getTexCoords().addAll(0, 0);
        mesh.getFaces().addAll(
                0, 0, 1, 0, 2, 0,
                2, 0, 1, 0, 3, 0
        );

        MeshView meshView = new MeshView(mesh);
        meshView.setMaterial(new PhongMaterial(Color.BLACK.deriveColor(0, 0, 0, opacity)));
        meshView.setDrawMode(DrawMode.FILL);
        meshView.setCullFace(CullFace.NONE);

        getChildren().add(meshView);
        return this;
    }

    public Plane render() {
        return render(-MainCamera.CAMERA_FAR_CLIP, MainCamera.CAMERA_FAR_CLIP,
                -MainCamera.CAMERA_FAR_CLIP, MainCamera.CAMERA_FAR_CLIP);
    }

    public Plane setColorOpacity(double opacity) {
        this.opacity = opacity;
        return this;
    }

    @Override
    public Point3D getNormalVector() {
        return normal;
    }

    @Override
    public double getRestitutionCoefficient() {
        return restitution;
    }

    public Plane setCoefficientOfRestitution(double elasticity) {
        restitution = elasticity;
        return this;
    }

    @Override
    public boolean isIntersecting(Point3D query) {
        // Direct plane equation check
        if (!isVisible() || Math.abs(normal.dotProduct(query) - cartesianConstant) > PLANE_INTERACTION_EPSILON)
            return false;
        // Intersection with the plane at some point is confirmed, but check last rendered bounds too
        Point3D relativePoint = query.subtract(startPoint);
        double projectedLambda = relativePoint.dotProduct(basis1) / basis1.dotProduct(basis1);
        double projectedMu = relativePoint.dotProduct(basis2) / basis2.dotProduct(basis2);
        return projectedLambda >= lambdaMin && projectedLambda <= lambdaMax
                && projectedMu >= muMin && projectedMu <= muMax;
    }

    @Override
    public void rotateAboutZBy(double angRad) {
        startPoint = Util.rotateAboutZ(startPoint, angRad);
        basis1 = Util.rotateAboutZ(basis1, angRad);
        basis2 = Util.rotateAboutZ(basis2, angRad);
        normal = basis1.crossProduct(basis2);
        cartesianConstant = normal.dotProduct(startPoint);
        if (lambdaMin != 0 || lambdaMax != 0 || muMin != 0 || muMax != 0)
            render(lambdaMin, lambdaMax, muMin, muMax);
    }

    @Override
    public Node copy() {
        Plane plane = new Plane(startPoint, basis1, basis2);
        plane.lambdaMin = lambdaMin;
        plane.lambdaMax = lambdaMax;
        plane.muMin = muMin;
        plane.muMax = muMax;
        plane.opacity = opacity;
        plane.restitution = restitution;
        if (lambdaMin != 0 || lambdaMax != 0 || muMin != 0 || muMax != 0)
            plane.render(lambdaMin, lambdaMax, muMin, muMax);
        if (Solid.OBJECTS.contains(this))
            plane.enablePhysicsInteractions();
        return plane;
    }
} 
