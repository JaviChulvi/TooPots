package es.uji.ei1027.toopots.model;

public class Prefiere {
    private String dniCliente;
    private int idTipoActividad;

    public Prefiere() {
    }

    public Prefiere(String dniCliente, int idTipoActividad) {
        this.dniCliente = dniCliente;
        this.idTipoActividad = idTipoActividad;
    }

    public String getDniCliente() {
        return dniCliente;
    }

    public void setDniCliente(String dniCliente) {
        this.dniCliente = dniCliente;
    }

    public int getIdTipoActividad() {
        return idTipoActividad;
    }

    public void setIdTipoActividad(int idTipoActividad) {
        this.idTipoActividad = idTipoActividad;
    }

    @Override
    public String toString() {
        return "Prefiere{" +
                "dniCliente='" + dniCliente + '\'' +
                ", idTipoActividad='" + idTipoActividad + '\'' +
                '}';
    }
}
