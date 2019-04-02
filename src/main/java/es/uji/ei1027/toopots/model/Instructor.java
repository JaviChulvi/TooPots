package es.uji.ei1027.toopots.model;

public class Instructor {

    private int id;
    private String estadoInstructor;
    private String nombre;
    private String correo;
    private String iban;

    public Instructor() {
        super();
    }

    public int getId() { return id; }

    public void setId(int id) { this.id = id; }

    public String getEstadoInstructor() {
        return estadoInstructor;
    }

    public void setEstadoInstructor(String estadoInstructor) {
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
                "id=" + id +
                ", estadoInstructor='" + estadoInstructor + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", iban='" + iban + '\'' +
                '}';
    }
}
