package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.TipoEntrada;

public class Entrada {

    private int id;
    private String tipo;
    private float precio;

    public Entrada() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) { this.tipo = tipo; }

    public float getPrecio() {
        return precio;
    }

    public void setPrecio(float precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return "Entrada{" +
                "id=" + id +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                '}';
    }
}
