package es.uji.ei1027.toopots.model;

import org.jasypt.util.password.BasicPasswordEncryptor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Date;

/////
//Clase que almacena, modifica y obtiene todos los datos de los cliente.
/////

public class Cliente {

    private String dni;
    private String password;
    private String password2;
    private String nombre;
    private String correo;
    private String genero;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate fechaNacimiento;

    public Cliente() {
        super();
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
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
        return "Cliente{" +
                "dni='" + dni + '\'' +
                ", password='" + password + '\'' +
                ", password2='" + password2 + '\'' +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", genero='" + genero + '\'' +
                ", fechaNacimiento=" + fechaNacimiento +
                '}';
    }
}
