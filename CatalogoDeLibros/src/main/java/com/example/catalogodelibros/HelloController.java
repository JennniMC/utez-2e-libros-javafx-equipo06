package com.example.catalogodelibros;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import com.example.catalogodelibros.model.Libro;
import com.example.catalogodelibros.service.LibroService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {


    @FXML private TableView<Libro> tablaLibros;

    @FXML private TableColumn<Libro, String> colId;
    @FXML private TableColumn<Libro, String> colTitulo;
    @FXML private TableColumn<Libro, String> colAutor;
    @FXML private TableColumn<Libro, Integer> colYear;
    @FXML private TableColumn<Libro, String> colGenero;
    @FXML private TableColumn<Libro, Boolean> colDisponible;

    @FXML private TextField txtId;
    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtYear;
    @FXML private ComboBox<String> cmbGenero;

    @FXML private CheckBox chkDisponible;

    @FXML
    public void initialize() {
        tablaLibros.setRowFactory(tv -> {
            TableRow<Libro> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getClickCount() == 2) {

                    Libro libro = row.getItem();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Datos del libro");
                    alert.setHeaderText(libro.getTitulo());

                    alert.setContentText(
                            "ID: " + libro.getId() + "\n" +
                                    "Autor: " + libro.getAutor() + "\n" +
                                    "Año: " + libro.getYear() + "\n" +
                                    "Género: " + libro.getGenero() + "\n" +
                                    "Disponible: " + (libro.isDisponible() ? "Sí" : "No")
                    );

                    alert.showAndWait();
                }
            });

            return row;
        });

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        colTitulo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitulo()));
        colAutor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAutor()));
        colYear.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getYear()));
        cmbGenero.setItems(FXCollections.observableArrayList(
                "Novela", "Ciencia", "Historia", "Tecnología", "Fantasía", "Romance"
        ));
        colGenero.setCellValueFactory(data ->
                new javafx.beans.property.SimpleStringProperty(data.getValue().getGenero())
        );

        colDisponible.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().isDisponible()));

        listaObservable = FXCollections.observableArrayList(libroService.consultarLibros());
        tablaLibros.setItems(listaObservable);


        tablaLibros.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtId.setText(newSel.getId());
                txtTitulo.setText(newSel.getTitulo());
                txtAutor.setText(newSel.getAutor());
                txtYear.setText(String.valueOf(newSel.getYear()));
               cmbGenero.setValue(newSel.getGenero());
                chkDisponible.setSelected(newSel.isDisponible());
            }
        });
        tablaLibros.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
    }

    private LibroService libroService = new LibroService();
    private ObservableList<Libro> listaObservable;
    private void mostrarAlerta(String titulo, String mensaje, Alert.AlertType tipo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    public void agregarLibro() {
        if (txtId.getText().isEmpty() || txtTitulo.getText().isEmpty() ||
                txtAutor.getText().isEmpty() || txtYear.getText().isEmpty() ||
                cmbGenero.getValue() == null) {

            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);

            return;
        }

        if (txtTitulo.getText().length() < 3) {
            mostrarAlerta("Error", "El titulo tiene que tener 3 letras", Alert.AlertType.ERROR);
            return;
        }

        if (txtAutor.getText().length() < 3) {
            mostrarAlerta("Error", "Autor minimo 3 letras", Alert.AlertType.ERROR);
            return;
        }

        int year;
        try {
            year = Integer.parseInt(txtYear.getText());
            if (year < 1500 || year > java.time.Year.now().getValue()) {
                mostrarAlerta("Error", "Año invalido", Alert.AlertType.ERROR);
                return;
            }
        } catch (Exception e) {
            mostrarAlerta("Error", "Solo se permiten Números Enteros, No letras ni decimales", Alert.AlertType.ERROR);
            return;
        }

        Libro libro = new Libro(
                txtId.getText(),
                txtTitulo.getText(),
                txtAutor.getText(),
                Integer.parseInt(txtYear.getText()),
                cmbGenero.getValue(),
                chkDisponible.isSelected()
        );


        String mensaje = libroService.agregarLibro(libro);
        mostrarAlerta("Message", mensaje, Alert.AlertType.INFORMATION);


        listaObservable.setAll(libroService.consultarLibros());

        // limpiar campos
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtYear.clear();
        chkDisponible.setSelected(false);
    }
    @FXML
    public void eliminarLibro() {

        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un libro", Alert.AlertType.ERROR);
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, "¿Quieres eliminar este libro?");
        confirm.setHeaderText(seleccionado.getTitulo());

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {

                libroService.eliminarLibro(seleccionado.getId());
                listaObservable.setAll(libroService.consultarLibros());

                mostrarAlerta("Éxito", "Libro eliminado correctamente", Alert.AlertType.INFORMATION);
                tablaLibros.getSelectionModel().clearSelection();
            }
        });
    }
    @FXML
    public void recargarDatos() {

        libroService.cargarDesdeArchivo();
        listaObservable.setAll(libroService.consultarLibros());

        // limpiar selección de la tabla
        tablaLibros.getSelectionModel().clearSelection();

        // limpiar campos manualmente
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtYear.clear();
        cmbGenero.setValue(null);
        chkDisponible.setSelected(false);

        mostrarAlerta("Actualizado", "Datos recargados correctamente", Alert.AlertType.INFORMATION);
    }
    @FXML
    public void modificarBook() {

        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();


        if (seleccionado == null) {
            mostrarAlerta("Error", "Selecciona un libro para modificar", Alert.AlertType.ERROR);
            return;
        }

        // VALIDACIONES DE CAMPOS
        if (txtId.getText().isEmpty() || txtTitulo.getText().isEmpty() ||
                txtAutor.getText().isEmpty() || txtYear.getText().isEmpty() ||
                cmbGenero.getValue() == null) {

            mostrarAlerta("Error", "Todos los campos son obligatorios", Alert.AlertType.ERROR);
            return;
        }

        int year;
        try {
            year = Integer.parseInt(txtYear.getText());
        } catch (Exception e) {
            mostrarAlerta("Error", "El año debe ser numérico", Alert.AlertType.ERROR);
            return;
        }

        Libro libroActualizado = new Libro(
                txtId.getText(),
                txtTitulo.getText(),
                txtAutor.getText(),
                year,
                cmbGenero.getValue(),
                chkDisponible.isSelected()
        );

        libroService.actualizarLibro(libroActualizado);
        listaObservable.setAll(libroService.consultarLibros());

        mostrarAlerta("Éxito", "Libro modificado correctamente", Alert.AlertType.INFORMATION);
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtYear.clear();
        cmbGenero.setValue(null);
        chkDisponible.setSelected(false);


        tablaLibros.getSelectionModel().clearSelection();
    }
    @FXML
    public void exportarReporte() {

        String mensaje = libroService.exportarReporte();

        mostrarAlerta("Resultado", mensaje, Alert.AlertType.INFORMATION);


        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Abrir archivo");
        confirm.setHeaderText("¿Deseas abrir el reporte?");
        confirm.setContentText("Se generó el archivo reporte_catalogo.csv");

        confirm.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                abrirArchivo();
            }
        });
    }

    private void abrirArchivo() {
        try {
            String ruta = System.getProperty("user.dir") + "/reporte_catalogo.csv";

            java.awt.Desktop.getDesktop().open(new java.io.File(ruta));

        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo abrir el archivo", Alert.AlertType.ERROR);
        }
    }





}
