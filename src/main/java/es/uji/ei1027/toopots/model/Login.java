package es.uji.ei1027.toopots.model;

public class Login {
    private String dni;
    private String password;

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Login{" +
                "dni='" + dni + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
