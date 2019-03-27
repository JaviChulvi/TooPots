package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.EstadoActividad;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Actividad {

    private int idActividad;
    private String tipoActividad;
    private String estadoActividad;
    private String nombre;
    private String descripción;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private int minAsistentes;
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

    public String getEstadoActividad() {
        return estadoActividad;
    }

    public void setEstadoActividad(String estadoActividad) {
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

    public int getMinAsistentes() {
        return minAsistentes;
    }

    public void setMinAsistentes(int minAsistentes) {
        this.minAsistentes = minAsistentes;
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
                ", minAsistentes=" + minAsistentes +
                ", lugarActividad='" + lugarActividad + '\'' +
                ", puntoEncuentro='" + puntoEncuentro + '\'' +
                ", textoCliente='" + textoCliente + '\'' +
                '}';
    }
}
