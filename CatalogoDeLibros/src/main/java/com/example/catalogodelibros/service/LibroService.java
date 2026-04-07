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

    public String agregarLibro(Libro libro) {

        for (Libro l : listLibros) {
            if (l.getId().equals(libro.getId())) {
                return "Libro duplicado";
            }
        }

        listLibros.add(libro);
        guardarEnArchivo();
        return "Libro agregado correctamente";
    }
    public List<Libro> consultarLibros() {
        return listLibros;
    }

    public String eliminarLibro(String id) {

        boolean eliminado = listLibros.removeIf(libro -> libro.getId().equals(id));

        if (eliminado) {
            guardarEnArchivo();
            return "Libro eliminado correctamente";
        } else {
            return "No se encontró el libro";
        }
    }

    public String actualizarLibro(Libro libroActualizado) {

        for (int i = 0; i < listLibros.size(); i++) {
            if (listLibros.get(i).getId().equals(libroActualizado.getId())) {

                listLibros.set(i, libroActualizado);
                guardarEnArchivo();
                return "Libro modificado con exito";
            }
        }

        return "No se encontró el libro";
    }

    public String guardarEnArchivo() {
        try {
            FileWriter writer = new FileWriter("libros.csv");

            for (Libro libro : listLibros) {
                writer.write(libro.toString() + "\n");
            }

            writer.flush();
            writer.close();

            return "Archivo guardado con exito";

        } catch (IOException e) {
            return "Error al guardar archivo";
        }
    }

    public String cargarDesdeArchivo() {
        listLibros.clear();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("libros.csv"));
            String linea;

            while ((linea = reader.readLine()) != null) {

                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(",");

                if (datos.length < 6) continue;

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

            return "Datos cargados con exito";


        } catch (IOException e) {
            return "El Archivo NO existe";
        }
    }
}