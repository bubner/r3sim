package me.bubner.r3sim;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

/**
 * R3Sim
 * 
 * @author Lucas Bubner, 2025
 */
public class R3Sim extends Application {
    private static final double CAMERA_INITIAL_DISTANCE = -1000;
    private static final double CAMERA_NEAR_CLIP = 0.1;
    private static final double CAMERA_FAR_CLIP = 10000.0;
    
    private final Group root = new Group();
    
    @Override
    public void start(Stage stage) {
        Camera camera = new MainCamera();
        
        camera.setNearClip(CAMERA_NEAR_CLIP);
        camera.setFarClip(CAMERA_FAR_CLIP);
        camera.setTranslateZ(CAMERA_INITIAL_DISTANCE);

        Scene scene = new Scene(root, 1024, 768, true);
        scene.setCamera(camera);
        scene.setFill(Color.GREY);

        stage.setTitle("R3 Sim");
        stage.setScene(scene);
        stage.show();

        root.getChildren().add(camera);
        root.getChildren().add(new World());
    }

    public static void main(String[] args) {
        launch(args);
    }
}