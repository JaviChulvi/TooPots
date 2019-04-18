package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class MonitorValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) { return Monitor.class.equals(cls); }

    @Override
    public void validate(Object target, Errors errors) {
        Monitor monitor = (Monitor) target;

        if (monitor.getDni().equals("")) {
            errors.rejectValue("dni", "obligatori", "Debes rellenar el campo");
        }

        if (monitor.getNombre().equals("")) {
            errors.rejectValue("nombre", "obligatori", "Debes rellenar el campo");
        }

        if (monitor.getIban().equals("")) {
            errors.rejectValue("iban", "obligatori", "Debes rellenar el campo");
        }

        if (monitor.getDomicilio().equals("")) {
            errors.rejectValue("domicilio", "obligatori", "Debes rellenar el campo");
        }


        if (!monitor.getEmail().contains("@") || !monitor.getEmail().contains(".") || monitor.getEmail().equals("")) {
            errors.rejectValue("email", "obligatori", "Introduce un email valido");
        }

        if (monitor.getPassword().equals("")) {
            errors.rejectValue("password", "valor incorrecte", "Debes rellenar el campo");
        } else if (monitor.getPassword2().equals("")) {
            errors.rejectValue("password2", "valor incorrecte", "Debes confirmar la contraseña");
        } else if (!monitor.getPassword().equals(monitor.getPassword2())){
            errors.rejectValue("password2", "valor incorrecte", "La contraseña de verificación no coincide.");
        }

    }
}
