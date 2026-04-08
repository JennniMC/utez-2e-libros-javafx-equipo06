module com.example.catalogodelibros {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.catalogodelibros to javafx.fxml;
    exports com.example.catalogodelibros;
}