module com {
    requires transitive javafx.controls;
    requires transitive javafx.fxml;
    requires transitive org.json;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    
    opens com.UserAsClient to javafx.fxml;
    exports com.UserAsClient;
    exports com.UserAsClient.Controller;
    opens com.UserAsClient.Controller to javafx.fxml;

    opens com.UserAsAdmin to javafx.fxml;
    exports com.UserAsAdmin;
    exports com.UserAsAdmin.Controller;
    opens com.UserAsAdmin.Controller to javafx.fxml;
}