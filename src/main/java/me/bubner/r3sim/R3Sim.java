package me.bubner.r3sim;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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
    private static final Color FILL_COLOUR = Color.GRAY
            .deriveColor(0, 0, 0, 0.7);
    private static Scene mainScene;
    private static World world;

    public static Scene getMainScene() {
        return mainScene;
    }

    public static World getWorld() {
        return world;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Group root3d = new Group();
        SubScene scene3d = new SubScene(root3d, 1024, 768, false, SceneAntialiasing.BALANCED);

        Pane hud = new Pane();
        hud.setPickOnBounds(false);
        hud.prefWidthProperty().bind(scene3d.widthProperty());
        hud.prefHeightProperty().bind(scene3d.heightProperty());

        StackPane root = new StackPane(scene3d, hud);
        mainScene = new Scene(root, 1024, 768);

        MainCamera camera = new MainCamera();
        scene3d.setCamera(camera.getCamera());
        scene3d.setFill(FILL_COLOUR);
        hud.getChildren().add(new AngleOverlay());
        world = new World();
        world.init();
        root3d.getChildren().addAll(camera, world);

        stage.setTitle("R3 Sim");
        stage.setResizable(false);
        stage.setScene(mainScene);
        stage.show();
    }
}