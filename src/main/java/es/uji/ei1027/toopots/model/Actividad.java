package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.EstadoActividad;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Time;
import java.util.Date;

public class Actividad {

    private int id;
    private String idTipoActividad;
    private String estado;
    private String nombre;
    private String descripción;
    @DateTimeFormat(pattern= "HH:MM")
    private Time duracion;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private int minAsistentes;
    private int maxAsistentes;
    private String lugar;
    private String puntoDeEncuentro;
    @DateTimeFormat(pattern= "HH:MM")
    private Time horaDeEncuentro;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(String idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
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

    public Time getDuracion() {
        return duracion;
    }

    public void setDuracion(Time duracion) {
        this.duracion = duracion;
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

    public int getMaxAsistentes() {
        return maxAsistentes;
    }

    public void setMaxAsistentes(int maxAsistentes) {
        this.maxAsistentes = maxAsistentes;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getPuntoDeEncuentro() {
        return puntoDeEncuentro;
    }

    public void setPuntoDeEncuentro(String puntoDeEncuentro) {
        this.puntoDeEncuentro = puntoDeEncuentro;
    }

    public Time getHoraDeEncuentro() {
        return horaDeEncuentro;
    }

    public void setHoraDeEncuentro(Time horaDeEncuentro) {
        this.horaDeEncuentro = horaDeEncuentro;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "id=" + id +
                ", idTipoActividad='" + idTipoActividad + '\'' +
                ", estado='" + estado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", descripción='" + descripción + '\'' +
                ", duracion=" + duracion +
                ", fecha=" + fecha +
                ", minAsistentes=" + minAsistentes +
                ", maxAsistentes=" + maxAsistentes +
                ", lugar='" + lugar + '\'' +
                ", puntoDeEncuentro='" + puntoDeEncuentro + '\'' +
                ", horaDeEncuentro=" + horaDeEncuentro +
                '}';
    }
}
