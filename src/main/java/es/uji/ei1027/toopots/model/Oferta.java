package es.uji.ei1027.toopots.model;

public class Oferta {

    private Actividad actividad;
    private Instructor instructor;

    public Oferta() {
        super();
    }

    public Actividad getActividad() {
        return actividad;
    }

    public void setActividad(Actividad actividad) {
        this.actividad = actividad;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "actividad=" + actividad +
                ", instructor=" + instructor +
                '}';
    }
}
