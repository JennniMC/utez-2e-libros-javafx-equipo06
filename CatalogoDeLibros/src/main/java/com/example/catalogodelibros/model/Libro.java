package com.example.catalogodelibros.model;

public class Libro {
    private String id;
    private String titulo;
    private String autor;
    private int year;
    private boolean disponible;

    public Libro(){

    }


    public Libro(String id, String titulo, String autor, int year, boolean disponible){
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.year = year;
        this.disponible = disponible;


    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    @Override
    public String toString(){
        return id + " , "+ titulo + " , " + autor + " , " + year + " , " + disponible;
    }

}
