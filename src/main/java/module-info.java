module com {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    
    opens com.UserAsClient to javafx.fxml;
    exports com.UserAsClient;
    exports com.UserAsClient.Controller;
    opens com.UserAsClient.Controller to javafx.fxml;
}