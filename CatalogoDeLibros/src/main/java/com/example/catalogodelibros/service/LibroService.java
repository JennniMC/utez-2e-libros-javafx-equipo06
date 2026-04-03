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

        for (Libro l : listLibros) {
            if (l.getId().equals(libro.getId())) {
                System.out.println("Libro duplicado");
                return;
            }
        }

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
            FileWriter writer = new FileWriter("libros.csv");
            for (Libro libro : listLibros) {
                writer.write(libro.toString() + "\n");
            }

            writer.flush();
            writer.close();

            System.out.println("Archivo guardado correctamente");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void cargarDesdeArchivo() {
        listLibros.clear();

        try {
            BufferedReader reader = new BufferedReader(new FileReader("libros.csv"));
            String linea;

            while ((linea = reader.readLine()) != null) {

                if (linea.trim().isEmpty()) continue; // 🔥 evita líneas vacías

                String[] datos = linea.split(",");

                if (datos.length < 6) continue; // 🔥 evita líneas incompletas

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
            System.out.println("Archivo no existe o está vacío");
        }
    }
}