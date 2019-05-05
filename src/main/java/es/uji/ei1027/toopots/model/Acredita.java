package es.uji.ei1027.toopots.model;

public class Acredita {
    private int tipoActividad;
    private String certificado;

    public int getTipoActividad() {
        return tipoActividad;
    }

    public void setTipoActividad(int tipoActividad) {
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
