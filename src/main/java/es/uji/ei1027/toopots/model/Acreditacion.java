package es.uji.ei1027.toopots.model;

public class Acreditacion {
    private String certificado;
    private int idInstructor;
    private String estado;

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    public int getIdInstructor() {
        return idInstructor;
    }

    public void setIdInstructor(int idInstructor) {
        this.idInstructor = idInstructor;
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
                ", idInstructor='" + idInstructor + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }
}
