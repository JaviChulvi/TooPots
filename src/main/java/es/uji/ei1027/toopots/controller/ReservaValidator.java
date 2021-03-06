package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Reserva;
import org.springframework.validation.Errors;

import org.springframework.validation.Validator;
import java.util.Arrays;
import java.util.List;

public class ReservaValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Reserva.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Reserva reserva = (Reserva) obj;

        int jubilados = reserva.getNumJubilados();
        int adultos = reserva.getNumAdultos();
        int menores = reserva.getNumMenores();

        if(jubilados + adultos + menores == 0) {
            errors.rejectValue("numJubilados", "obligatorio", "Tienes que reservar al menos una plaza.");
            errors.rejectValue("numAdultos", "obligatorio", "Tienes que reservar al menos una plaza.");
            errors.rejectValue("numMenores", "obligatorio", "Tienes que reservar al menos una plaza.");
        } else if(jubilados + adultos == 0) {
            errors.rejectValue("numMenores", "obligatorio", "Tienes que reservar al menos una plaza de adulto o jubilado.");
        }
    }

}
