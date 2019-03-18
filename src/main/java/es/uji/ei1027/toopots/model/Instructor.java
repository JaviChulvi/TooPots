package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.EstadoInstructor;

public class Instructor {

    private String dni;
    private EstadoInstructor estadoInstructor;
    private String nombre;
    private String correo;
    private String iban;

    public Instructor() {
        super();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public EstadoInstructor getEstadoInstructor() {
        return estadoInstructor;
    }

    public void setEstadoInstructor(EstadoInstructor estadoInstructor) {
        this.estadoInstructor = estadoInstructor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    @Override
    public String toString() {
        return "Instructor{" +
                "dni='" + dni + '\'' +
                ", estadoInstructor=" + estadoInstructor +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", iban='" + iban + '\'' +
                '}';
    }
}
