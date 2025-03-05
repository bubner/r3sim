module me.bubner.r3sim {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    opens me.bubner.r3sim to javafx.fxml;
    exports me.bubner.r3sim;
}