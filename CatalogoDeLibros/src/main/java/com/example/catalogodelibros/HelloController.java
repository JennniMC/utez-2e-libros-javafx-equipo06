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
    @FXML private TextField txtGenero;
    @FXML private Label lblMensaje;

    @FXML private CheckBox chkDisponible;

    @FXML
    public void initialize() {

        colId.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getId()));
        colTitulo.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTitulo()));
        colAutor.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getAutor()));
        colYear.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().getYear()));
        colGenero.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getGenero()));
        colDisponible.setCellValueFactory(data -> new javafx.beans.property.SimpleObjectProperty<>(data.getValue().isDisponible()));

        listaObservable = FXCollections.observableArrayList(libroService.consultarLibros());
        tablaLibros.setItems(listaObservable);


        tablaLibros.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                txtId.setText(newSel.getId());
                txtTitulo.setText(newSel.getTitulo());
                txtAutor.setText(newSel.getAutor());
                txtYear.setText(String.valueOf(newSel.getYear()));
                txtGenero.setText(newSel.getGenero());
                chkDisponible.setSelected(newSel.isDisponible());
            }
        });
    }

    private LibroService libroService = new LibroService();
    private ObservableList<Libro> listaObservable;

    @FXML
    public void agregarLibro() {

        Libro libro = new Libro(
                txtId.getText(),
                txtTitulo.getText(),
                txtAutor.getText(),
                Integer.parseInt(txtYear.getText()),
                txtGenero.getText(),
                chkDisponible.isSelected()
        );


        String mensaje = libroService.agregarLibro(libro);
        lblMensaje.setText(mensaje);

        listaObservable.setAll(libroService.consultarLibros());

        // limpiar campos
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtYear.clear();
        txtGenero.clear();
        chkDisponible.setSelected(false);
    }

    @FXML
    public void eliminarLibro() {
        Libro seleccionado = tablaLibros.getSelectionModel().getSelectedItem();

        if (seleccionado != null) {
            libroService.eliminarLibro(seleccionado.getId());
            listaObservable.setAll(libroService.consultarLibros());
            txtId.clear();
            txtTitulo.clear();
            txtAutor.clear();
            txtYear.clear();
            txtGenero.clear();
            chkDisponible.setSelected(false);

            lblMensaje.setText("Libro eliminado con exito");
            lblMensaje.setStyle("-fx-text-fill: green;");
        } else {
            lblMensaje.setText("Seleccione un libro para eliminar :)");
            lblMensaje.setStyle("-fx-text-fill: red;");
        }
    }

    @FXML
    public void recargarDatos() {
        String mensaje = libroService.cargarDesdeArchivo();
        listaObservable.setAll(libroService.consultarLibros());
        lblMensaje.setText(mensaje);
    }
    @FXML
    public void modificarBook() {

        Libro libroActualizado = new Libro(
                txtId.getText(),
                txtTitulo.getText(),
                txtAutor.getText(),
                Integer.parseInt(txtYear.getText()),
                txtGenero.getText(),
                chkDisponible.isSelected()
        );

        libroService.actualizarLibro(libroActualizado);

        listaObservable.setAll(libroService.consultarLibros());

        // limpiar campos
        txtId.clear();
        txtTitulo.clear();
        txtAutor.clear();
        txtYear.clear();
        txtGenero.clear();
        chkDisponible.setSelected(false);
        lblMensaje.setText("Libro modificado correctamente");
        lblMensaje.setStyle("-fx-text-fill: green;");
    }





}
