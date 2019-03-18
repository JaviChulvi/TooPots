package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.EstadoActividad;

import java.util.Date;

public class Actividad {

    private int idActividad;
    private String tipoActividad;
    private EstadoActividad estadoActividad;
    private String nombre;
    private String descripción;
    private Date fecha;
    private Double precio;
    private int minAsistentes;
    private int maxAsistentes;
    private String lugarActividad;
    private String puntoEncuentro;
    private String textoCliente;


    public Actividad() {
        super();
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public EstadoActividad getEstadoActividad() {
        return estadoActividad;
    }

    public void setEstadoActividad(EstadoActividad estadoActividad) {
        this.estadoActividad = estadoActividad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripción() {
        return descripción;
    }

    public void setDescripción(String descripción) {
        this.descripción = descripción;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public int getMinAsistentes() {
        return minAsistentes;
    }

    public void setMinAsistentes(int minAsistentes) {
        this.minAsistentes = minAsistentes;
    }

    public int getMaxAsistentes() {
        return maxAsistentes;
    }

    public void setMaxAsistentes(int maxAsistentes) {
        this.maxAsistentes = maxAsistentes;
    }

    public String getLugarActividad() {
        return lugarActividad;
    }

    public void setLugarActividad(String lugarActividad) {
        this.lugarActividad = lugarActividad;
    }

    public String getPuntoEncuentro() {
        return puntoEncuentro;
    }

    public void setPuntoEncuentro(String puntoEncuentro) {
        this.puntoEncuentro = puntoEncuentro;
    }

    public String getTextoCliente() {
        return textoCliente;
    }

    public void setTextoCliente(String textoCliente) {
        this.textoCliente = textoCliente;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "idActividad=" + idActividad +
                ", tipoActividad='" + tipoActividad + '\'' +
                ", estadoActividad=" + estadoActividad +
                ", nombre='" + nombre + '\'' +
                ", descripción='" + descripción + '\'' +
                ", fecha=" + fecha +
                ", precio=" + precio +
                ", minAsistentes=" + minAsistentes +
                ", maxAsistentes=" + maxAsistentes +
                ", lugarActividad='" + lugarActividad + '\'' +
                ", puntoEncuentro='" + puntoEncuentro + '\'' +
                ", textoCliente='" + textoCliente + '\'' +
                '}';
    }
}
