package com.example.catalogodelibros.service;
//Es la clase que maneja toda la lógica:
//CRUD
//archivos
//validaciones básicas

import com.example.catalogodelibros.model.Libro;
import java.util.ArrayList;
import java.util.List;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class LibroService {

    private List<Libro> listLibros = new ArrayList<>();//Guarda los datos temporalmente

    public LibroService() {
        cargarDesdeArchivo();
    }// Al iniciar el sistema, carga los datos del archivo

    public String agregarLibro(Libro libro) {

          //for each
        for (Libro l : listLibros) {
            //Si el ID coincide manda el mensaje
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
    }//Devuelve todos los libros

    public String eliminarLibro(String id) { // Elimina por ID y Guarda cambios

        boolean eliminado = listLibros.removeIf(libro -> libro.getId().equals(id));

        if (eliminado) {
            guardarEnArchivo();
            return "Libro eliminado correctamente";
        } else {
            return "No se encontró el libro";
        }
    }

    public String actualizarLibro(Libro libroActualizado) {//Busca por ID y Reemplaza el libro

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

                String ruta = System.getProperty("user.dir") + "/data/libros.csv"; //Guarda en carpeta data


                java.io.File carpeta = new java.io.File(System.getProperty("user.dir") + "/data");
                if (!carpeta.exists()) {
                    carpeta.mkdirs();//Crea la carpeta si no existe
                }

                FileWriter writer = new FileWriter(ruta); //Escribe en archivo

                for (Libro libro : listLibros) {
                    writer.write(libro.toString() + "\n");
                }

                writer.close();

                return "Archivo guardado con exito";

            } catch (IOException e) {
                e.printStackTrace();
                return "Error al guardar archivo";
            }

    }

    public String cargarDesdeArchivo() {
        listLibros.clear();

        try {

            String ruta = System.getProperty("user.dir") + "/data/libros.csv";

            BufferedReader reader = new BufferedReader(new FileReader(ruta)); // Lee línea por línea
            String linea;

            while ((linea = reader.readLine()) != null) {

                if (linea.trim().isEmpty()) continue;

                String[] datos = linea.split(","); //Convierte CSV en datos

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
            return "Archivo no encontrado, se creará uno nuevo";
        }
    }

    public String exportarReporte() {
        try {
            FileWriter writer = new FileWriter("reporte_catalogo.csv");

            writer.write("ID,Titulo,Autor,Año,Genero,Disponible\n");

            for (Libro libro : listLibros) {
                writer.write(libro.toString() + "\n");
            }

            writer.close();
            return "Reporte exportado";
        } catch (IOException e) {
            return "Error al exportar";
        }
    }
}