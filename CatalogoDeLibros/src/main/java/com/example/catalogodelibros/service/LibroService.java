package com.example.catalogodelibros.service;

import com.example.catalogodelibros.model.Libro;
import java.util.ArrayList;
import java.util.List;

public class LibroService {

//Array donde se guardaran los libros
    private List<Libro> listLibros = new ArrayList<>();

    //Es el metodo para agregar un libro
    public void addLibro(Libro libro) {
        listLibros.add(libro);
    }

    //Es el método para consultar un libro
    public List<Libro> ConsultarLibros() {
        return listLibros;
    }

    //Método para eliminar un libro
    public void deleteLibro(String id) {
        listLibros.removeIf(libro -> libro.getId().equals(id));
    }

   //Método para avtualizar un libro
    public void actualizarLibro(Libro libroActualizado) {
        for (int i = 0; i < listLibros.size(); i++) {
            if (listLibros.get(i).getId().equals(libroActualizado.getId())) {
                listLibros.set(i, libroActualizado);
                break;
            }
        }
    }

}