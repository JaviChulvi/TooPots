package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Login;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class LoginValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Login.class.equals(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Login login = (Login) target;
        if (login.getDni().equals("")) {
            errors.rejectValue("dni", "obligatorio", "Rellena el campo");
        }
        if (login.getPassword().equals("")) {
            errors.rejectValue("password", "obligatorio", "Rellena el campo");
        }

        /*if (login.getImagen() != "") {
            errors.rejectValue("imagen", "obligatorio", "Tiene que añadir una imagen");
        }*/
    }
}