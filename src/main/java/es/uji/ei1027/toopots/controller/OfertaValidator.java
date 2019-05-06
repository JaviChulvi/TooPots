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

        if(oferta.getNombre().equals("") || oferta.getNombre() == null) {
            errors.rejectValue("nombre", "obligatorio", "Campo obligatorio.");
        }

        if(oferta.getDescuento() == 0f) {
            errors.rejectValue("descuento", "obligatorio", "Campo obligatorio.");
        }

    }
}
