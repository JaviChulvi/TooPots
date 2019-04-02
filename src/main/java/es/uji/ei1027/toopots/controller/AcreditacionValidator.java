package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Acreditacion;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AcreditacionValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) { return Acreditacion.class.equals(cls); }

    @Override
    public void validate(Object target, Errors errors) {
        Acreditacion acreditacion = new Acreditacion();

        if(acreditacion.getEstado().equals("")) {
            errors.rejectValue("estado", "Valor necesario",
                    "Necesitas elegir un estado");
        }
    }
}
