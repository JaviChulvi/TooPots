package es.uji.ei1027.toopots.model;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class Monitor {

    private String dni;
    private String password;
    private String password2;
    private String estado = "pendiente";
    private String nombre;
    private String domicilio;
    private String email;
    private String iban;
    private String foto;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String correo) {
        this.email = correo;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getDomicilio() {
        return domicilio;
    }

    public void setDomicilio(String domicilio) {
        this.domicilio = domicilio;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword2() {
        return password2;
    }

    public void setPassword2(String password2) {
        this.password2 = password2;
    }


    public void cifrarContraseña() {
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        this.password = passwordEncryptor.encryptPassword(this.password2);
    }
    @Override
    public String toString() {
        return "Monitor{" +
                "dni='" + dni + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", estado='" + estado + '\'' +
                ", nombre='" + nombre + '\'' +
                ", domicilio='" + domicilio + '\'' +
                ", email='" + email + '\'' +
                ", iban='" + iban + '\'' +
                ", foto='" + foto + '\'' +
                '}';
    }


}
