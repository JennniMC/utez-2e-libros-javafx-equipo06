package com.example.catalogodelibros.service;

import com.example.catalogodelibros.model.Libro;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class LibroService {

    private List<Libro> listLibros = new ArrayList<>();

    public LibroService() {
        cargarDesdeArchivo();
    }

    public void agregarLibro(Libro libro) {
        listLibros.add(libro);
        guardarEnArchivo();
    }

    public List<Libro> consultarLibros() {
        return listLibros;
    }

    public void eliminarLibro(String id) {
        listLibros.removeIf(libro -> libro.getId().equals(id));
        guardarEnArchivo();
    }

    public void actualizarLibro(Libro libroActualizado) {
        for (int i = 0; i < listLibros.size(); i++) {
            if (listLibros.get(i).getId().equals(libroActualizado.getId())) {
                listLibros.set(i, libroActualizado);
                break;
            }
        }
        guardarEnArchivo();
    }

    public void guardarEnArchivo() {
        try {
            FileWriter writer = new FileWriter("data/libro.csv");

            for (Libro libro : listLibros) {
                writer.write(libro.toString() + "\n");
            }

            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDesdeArchivo() {
        listLibros.clear();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("data/libro.csv"));
            String linea;

            while ((linea = reader.readLine()) != null) {
                String[] datos = linea.split(",");

                Libro libro = new Libro(
                        datos[0],
                        datos[1],
                        datos[2],
                        Integer.parseInt(datos[3]),
                        datos[4],
                        Boolean.parseBoolean(datos[5])
                );

                listLibros.add(libro);
            }

            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}