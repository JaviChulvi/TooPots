package es.uji.ei1027.toopots.model;

public class PasarelaPago {
    private String titular;
    private String numeroTarjeta;
    private String codigoSecreto;

    public PasarelaPago() {
    }

    public String getTitular() {
        return titular;
    }

    public void setTitular(String titular) {
        this.titular = titular;
    }

    public String getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public void setNumeroTarjeta(String numeroTarjeta) {
        this.numeroTarjeta = numeroTarjeta;
    }

    public String getCodigoSecreto() {
        return codigoSecreto;
    }

    public void setCodigoSecreto(String codigoSecreto) {
        this.codigoSecreto = codigoSecreto;
    }
}
