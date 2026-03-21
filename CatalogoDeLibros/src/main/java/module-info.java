module com.example.catalogodelibros {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;

    opens com.example.catalogodelibros to javafx.fxml;
    exports com.example.catalogodelibros;
}