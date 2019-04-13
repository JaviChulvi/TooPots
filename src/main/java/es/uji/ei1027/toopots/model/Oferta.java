package es.uji.ei1027.toopots.model;

public class Oferta {

    private int idActividad;
    private String dniMonitor;

    public Oferta() {
        super();
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getDniMonitor() {
        return dniMonitor;
    }

    public void setDniMonitor(String dniMonitor) {
        this.dniMonitor = dniMonitor;
    }

    @Override
    public String toString() {
        return "Oferta{" +
                "idActividad=" + idActividad +
                ", dniMonitor='" + dniMonitor + '\'' +
                '}';
    }
}
