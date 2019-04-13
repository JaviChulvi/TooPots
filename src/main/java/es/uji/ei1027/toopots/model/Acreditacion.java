package es.uji.ei1027.toopots.model;

public class Acreditacion {
    private String certificado;
    private String dniMonitor;
    private String estado;

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public String getDniMonitor() {
        return dniMonitor;
    }

    public void setIdInstructor(String idInstructor) {
        this.dniMonitor = idInstructor;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Acreditacion{" +
                "certificado=" + certificado +
                ", idInstructor='" + dniMonitor + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
