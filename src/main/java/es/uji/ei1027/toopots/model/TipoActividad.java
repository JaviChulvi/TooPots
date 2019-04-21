package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.NivelActividad;

public class TipoActividad {
    private int id;
    private String nombre;
    private String nivelActividad;

    public TipoActividad() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNivelActividad() {
        return nivelActividad;
    }

    public void setNivelActividad(String nivelActividad) {
        this.nivelActividad = nivelActividad;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "TipoActividad{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nivelActividad=" + nivelActividad +
                '}';
    }
}
