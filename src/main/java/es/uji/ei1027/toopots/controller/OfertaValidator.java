package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Oferta;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class OfertaValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Oferta.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Oferta oferta = (Oferta) obj;
        /**if (nadador.getNumTransaccion().equals("")) {
         errors.rejectValue("nom", "obligatori", "Cal introduir un valor");
         }*/
        /*if (oferta.getIdActividad() <= 0) {
            errors.rejectValue("id", "obligatori", "Debes rellenar el campo");
        }*/

        /*if (oferta.getActividad() == null || oferta.getActividad() <= 0) {
            errors.rejectValue("actividad", "obligatori", "Debes rellenar el campo");
        }

        if (oferta.getInstructor() == null || oferta.getInstructor() <= 0) {
            errors.rejectValue("instructor", "obligatori", "Debes rellenar el campo");
        }*/

    }
}
