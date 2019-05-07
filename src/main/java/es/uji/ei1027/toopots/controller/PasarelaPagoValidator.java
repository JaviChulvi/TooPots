package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.PasarelaPago;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class PasarelaPagoValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return PasarelaPago.class.equals(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        PasarelaPago pasarelaPago = (PasarelaPago) target;

        if (pasarelaPago.getTitular().equals("")) {
            errors.rejectValue("titular", "obligatori", "Debes rellenar el campo");
        }

        if (pasarelaPago.getNumeroTarjeta().equals("")) {
            errors.rejectValue("numeroTarjeta", "obligatori", "Debes rellenar el campo");
        }

        if (pasarelaPago.getCodigoSecreto().equals("")) {
            errors.rejectValue("codigoSecreto", "obligatori", "Debes rellenar el campo");
        }
    }
}
