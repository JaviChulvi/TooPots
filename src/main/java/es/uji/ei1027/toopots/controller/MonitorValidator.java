package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MonitorValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) { return Monitor.class.equals(cls); }

    @Override
    public void validate(Object target, Errors errors) {
        Monitor monitor = (Monitor) target;
        ArrayList<Character> vec = new ArrayList<>();
        vec.add('0');
        vec.add('1');
        vec.add('2');
        vec.add('3');
        vec.add('4');
        vec.add('5');
        vec.add('6');
        vec.add('7');
        vec.add('8');
        vec.add('9');
        if (monitor.getDni().equals("")) {
            errors.rejectValue("dni", "obligatori", "Debes rellenar el campo");
        }

        if (monitor.getDni().length() == 9 && Character.isLetter(monitor.getDni().charAt(8))== true) {

            for(int i=1 ; i<8 ; i++){
                if(!vec.contains(monitor.getDni().charAt(i))){
                    errors.rejectValue("dni", "obligatori", "El dni no coincide con el formato estandar");
                    break;
                }
            }

        }else {
            errors.rejectValue("dni", "obligatori", "El dni no coincide con el formato estandar");
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
