package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Descuento;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class DescuentoValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Descuento.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Descuento descuento = (Descuento) obj;

        if(descuento.getNombre().equals("") || descuento.getNombre() == null) {
            errors.rejectValue("nombre", "obligatorio", "Campo obligatorio.");
        }

        if(descuento.getDescuento() == 0f) {
            errors.rejectValue("descuento", "obligatorio", "Campo obligatorio.");
        }

    }
}
