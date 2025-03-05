module me.bubner.r3sim {
    requires javafx.controls;
    requires javafx.fxml;


    opens me.bubner.r3sim to javafx.fxml;
    exports me.bubner.r3sim;
}