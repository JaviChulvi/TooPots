package es.uji.ei1027.toopots.model;

public class Oferta {

    private Integer id;
    private Integer actividad;
    private Integer instructor;

    public Oferta() {
        super();
    }


    public Integer getId() {
        return id;
    }

    public Integer getActividad() {
        return actividad;
    }

    public Integer getInstructor() {
        return instructor;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setActividad(Integer actividad) {
        this.actividad = actividad;
    }

    public void setInstructor(Integer instructor) {
        this.instructor = instructor;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "id=" + id +
                ", actividad=" + actividad +
                ", instructor=" + instructor +
                '}';
    }
}
