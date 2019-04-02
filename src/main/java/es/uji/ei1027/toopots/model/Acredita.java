package es.uji.ei1027.toopots.model;

public class Acredita {
    private String tipoActividad;
    private String certificado;

    public String getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(String tipoActividad) {
        this.tipoActividad = tipoActividad;
    }

    public String getCertificado() {
        return certificado;
    }

    public void setCertificado(String certificado) {
        this.certificado = certificado;
    }

    @Override
    public String toString() {
        return "Acredita{" +
                "tipoActividad=" + tipoActividad +
                ", certificado='" + certificado + '\'' +
                '}';
    }
}
