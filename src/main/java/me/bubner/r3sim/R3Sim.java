package me.bubner.r3sim;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import me.bubner.r3sim.camera.AngleOverlay;
import me.bubner.r3sim.camera.MainCamera;

/**
 * R3Sim
 *
 * @author Lucas Bubner, 2025
 */
public class R3Sim extends Application {
    private static Scene scene;
    private final Group root = new Group();

    public static Scene getScene() {
        return scene;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        scene = new Scene(root, 1024, 768, true);
        MainCamera camera = new MainCamera();
        scene.setCamera(camera);
        scene.setFill(Color.BLACK);

        root.getChildren().addAll(
                camera,
                new AngleOverlay(),
                new World()
        );
        
        stage.setTitle("R3 Sim");
        stage.setScene(scene);
        stage.show();
    }
}