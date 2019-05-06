package es.uji.ei1027.toopots.model;

public class Oferta {

    private String nombre;
    private String descripcion;
    private float descuento;

    public Oferta() {
        super();
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public float getDescuento() { return descuento; }

    public void setDescuento(float descuento) { this.descuento = descuento; }

    @Override
    public String toString() {
        return "Oferta{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", descuento=" + descuento +
                '}';
    }
}
