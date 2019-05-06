package es.uji.ei1027.toopots.model;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class Reserva {

    private int idActividad;
    private String dniCliente;
    private String estadoPago;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    private int numJubilados;
    private int numAdultos;
    private int numMenores;
    private double precioPorPersona;

    public Reserva() {
        super();
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

    public String getEstadoPago() {
        return estadoPago;
    }

    public void setEstadoPago(String estadoPago) {
        this.estadoPago = estadoPago;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public int getNumAdultos() {
        return numAdultos;
    }

    public void setNumAdultos(int numAdultos) {
        this.numAdultos = numAdultos;
    }

    public int getNumMenores() {
        return numMenores;
    }

    public void setNumMenores(int numMenores) {
        this.numMenores = numMenores;
    }

    public double getPrecioPorPersona() {
        return precioPorPersona;
    }

    public void setPrecioPorPersona(double precioPorPersona) { this.precioPorPersona = precioPorPersona; }

    public int getNumJubilados() { return numJubilados; }

    public void setNumJubilados(int numJubilados) { this.numJubilados = numJubilados; }

    @Override
    public String toString() {
        return "Reserva{" +
                "idActividad=" + idActividad +
                ", dniCliente='" + dniCliente + '\'' +
                ", estadoPago='" + estadoPago + '\'' +
                ", fecha=" + fecha +
                ", numJubilados=" + numJubilados +
                ", numAdultos=" + numAdultos +
                ", numMenores=" + numMenores +
                ", precioPorPersona=" + precioPorPersona +
                '}';
    }
}
