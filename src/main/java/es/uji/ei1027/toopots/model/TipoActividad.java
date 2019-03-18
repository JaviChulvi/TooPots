package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.NivelActividad;

public class TipoActividad {

    private String nombre;
    private NivelActividad nivelActividad;

    public TipoActividad() {
        super();
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public NivelActividad getNivelActividad() {
        return nivelActividad;
    }

    public void setNivelActividad(NivelActividad nivelActividad) {
        this.nivelActividad = nivelActividad;
    }

    @Override
    public String toString() {
        return "TipoActividad{" +
                "nombre='" + nombre + '\'' +
                ", nivelActividad=" + nivelActividad +
                '}';
    }
}
