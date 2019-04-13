package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.EstadoReserva;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Reserva {

    private Integer numTransaccion;
    private int idActividad;
    private String dniCliente;
    private String estado;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private int numAsistentes;
    private Double precioPersona;
    private Double precioTotal;

    public Reserva() {
        super();
    }

    public Integer getNumTransaccion() {
        return numTransaccion;
    }

    public void setNumTransaccion(Integer numTransaccion) {
        this.numTransaccion = numTransaccion;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNumAsistentes() {
        return numAsistentes;
    }

    public void setNumAsistentes(int numAsistentes) {
        this.numAsistentes = numAsistentes;
    }

    public Double getPrecioPersona() {
        return precioPersona;
    }

    public void setPrecioPersona(Double precioPersona) {
        this.precioPersona = precioPersona;
    }

    public Double getPrecioTotal() {
        return precioTotal;
    }

    public void setPrecioTotal(Double precioTotal) {
        this.precioTotal = precioTotal;
    }

    @Override
    public String toString() {
        return "Reserva{" +
                "numTransaccion=" + numTransaccion +
                ", idActividad=" + idActividad +
                ", dniCliente='" + dniCliente + '\'' +
                ", estado='" + estado + '\'' +
                ", fecha=" + fecha +
                ", numAsistentes=" + numAsistentes +
                ", precioPersona=" + precioPersona +
                ", precioTotal=" + precioTotal +
                '}';
    }
}
