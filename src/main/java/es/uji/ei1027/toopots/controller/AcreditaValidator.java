package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Acredita;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class AcreditaValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) { return Acredita.class.equals(cls); }

    @Override
    public void validate(Object target, Errors errors) {
        Acredita acredita = new Acredita();

        if(acredita.getCertificado().equals("")) {
            errors.rejectValue("certificado", "Valor necesario",
                    "Necesitas elegir un certificado");
        }

        if(acredita.getTipoActividad().equals("")) {
            errors.rejectValue("tipoactividad", "Valor necesario",
                    "Necesitas elegir un tipo de actividad");
        }
    }
}
