package com.example.ricindigus.newsapp;

public class NewsItem {
    private String titulo;
    private String resumen;
    private String fecha;
    private String imagen;
    private String web;

    public NewsItem(String titulo, String resumen, String fecha, String imagen, String web) {
        this.titulo = titulo;
        this.resumen = resumen;
        this.fecha = fecha;
        this.imagen = imagen;
        this.web = web;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web;
    }
}
