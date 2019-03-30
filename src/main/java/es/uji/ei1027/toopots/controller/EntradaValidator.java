package es.uji.ei1027.toopots.controller;


import es.uji.ei1027.toopots.model.Entrada;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class EntradaValidator implements Validator {


    @Override
    public boolean supports(Class<?> cls) {
        return Entrada.class.equals(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Entrada entrada = (Entrada) target;

        List<String> valores = Arrays.asList("menor18", "entre18-50", "mayor50", "grup",
                "temporadabaixa", "temporadaalta");

        if (! valores.contains(entrada.getTipo())) {
            errors.rejectValue("tipo", "valor incorrecto", "Debes seleccionar un tipo de la lista");
        }

        if (entrada.getPrecio() <= 0) {
            errors.rejectValue("precio", "valor incorrecto", "Precio no puede ser menor o igual a 0.");
        }

    }
}
