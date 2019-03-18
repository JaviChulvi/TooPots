package es.uji.ei1027.toopots.model;

import es.uji.ei1027.toopots.model.tipos.EstadoReserva;

import java.util.Date;

public class Reserva {

    private String numTransaccion;
    private int actividad;
    private String cliente;
    private String estadoReserva;
    private Date fechaReserva;
    private int numAsistentes;
    private Double precioPersona;

    public Reserva() {
        super();
    }

    public String getNumTransaccion() {
        return numTransaccion;
    }

    public void setNumTransaccion(String numTransaccion) {
        this.numTransaccion = numTransaccion;
    }

    public int getActividad() {
        return actividad;
    }

    public void setActividad(int id_actividad) {
        this.actividad = id_actividad;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String dni_cliente) {
        this.cliente = dni_cliente;
    }

    public String getEstadoReserva() {
        return estadoReserva;
    }

    public void setEstadoReserva(String estadoReserva) {
        this.estadoReserva = estadoReserva;
    }

    public Date getFechaReserva() {
        return fechaReserva;
    }

    public void setFechaReserva(Date fechaReserva) {
        this.fechaReserva = fechaReserva;
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

    @Override
    public String toString() {
        return "Reserva{" +
                "numTransaccion='" + numTransaccion + '\'' +
                ", actividad=" + actividad +
                ", cliente=" + cliente +
                ", estadoReserva=" + estadoReserva +
                ", fechaReserva=" + fechaReserva +
                ", numAsistentes=" + numAsistentes +
                ", precioPersona=" + precioPersona +
                '}';
    }
}
