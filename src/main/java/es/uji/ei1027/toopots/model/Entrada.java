package es.uji.ei1027.toopots.model;

/////
//Clase que almacena, modifica y obtiene todos los datos de las entradas.
/////

public class Entrada {

    private int idActividad;
    private String tipo;
    private float precio;

    public Entrada() {
        super();
    }

    public Entrada(int idActividad, String tipo, float precio) {
        this.idActividad = idActividad;
        this.tipo = tipo;
        this.precio = precio;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int id) {
        this.idActividad = id;
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
                "id=" + idActividad +
                ", tipo='" + tipo + '\'' +
                ", precio=" + precio +
                '}';
    }
}
