package es.uji.ei1027.toopots.model;

public class Descuento {

    private String nombre;
    private String descripcion;
    private float descuento;
    private String tipo;

    public Descuento() {
        super();
    }

    public String getNombre() { return nombre; }

    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDescripcion() { return descripcion; }

    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public float getDescuento() { return descuento; }

    public void setDescuento(float descuento) { this.descuento = descuento; }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "Descuento{" +
                "nombre='" + nombre + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", descuento=" + descuento +
                ", tipo='" + tipo + '\'' +
                '}';
    }
}
