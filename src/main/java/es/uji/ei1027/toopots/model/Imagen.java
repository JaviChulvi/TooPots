package es.uji.ei1027.toopots.model;

/////
//Clase que almacena, modifica y obtiene todos los datos de las imagenes.
/////

public class Imagen {

    private int idActividad;
    private String imagen;

    public Imagen() {
        super();
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int id) {
        this.idActividad = id;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + idActividad +
                ", imagen=" + imagen +
                '}';
    }
}
