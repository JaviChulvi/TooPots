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
        if (reserva.getNumTransaccion().equals("")) {
            errors.rejectValue("nom", "obligatori", "Cal introduir un valor");
        }


        List<String> valors = Arrays.asList("pendent", "pagat");

        if(!valors.contains(reserva.getEstadoReserva())) {
            errors.rejectValue("estadoReserva", "valor incorrecte", "Deu ser: pendent o pagat");
        }

    }

}
