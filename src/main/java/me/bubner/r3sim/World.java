package me.bubner.r3sim;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class World extends Group {
    public World() {
        PhongMaterial phongMaterial = new PhongMaterial();
        phongMaterial.setDiffuseColor(Color.RED);
        phongMaterial.setSpecularColor(Color.RED);
        Box box = new Box(100, 100, 100);
        box.setMaterial(phongMaterial);
        box.setVisible(true);
        getChildren().add(box);
    }
}
