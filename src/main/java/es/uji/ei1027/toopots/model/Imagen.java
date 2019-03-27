package es.uji.ei1027.toopots.model;

public class Imagen {

    private int id;
    private byte imagen;

    public Imagen() {
        super();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public byte getImagen() {
        return imagen;
    }

    public void setImagen(byte imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        return "Imagen{" +
                "id=" + id +
                ", imagen=" + imagen +
                '}';
    }
}
