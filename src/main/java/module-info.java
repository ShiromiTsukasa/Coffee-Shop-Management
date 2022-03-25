module com.main.coffee_shop_management {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.main.coffee_shop_management to javafx.fxml;
    exports com.main.coffee_shop_management;
    exports com.main.Controller;
    opens com.main.Controller to javafx.fxml;
}