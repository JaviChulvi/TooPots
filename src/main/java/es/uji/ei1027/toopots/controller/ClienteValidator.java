package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Cliente;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;
import java.util.List;

public class ClienteValidator  implements Validator {

    @Override
    public boolean supports(Class<?> cls) {
        return Cliente.class.equals(cls);
    }

    @Override
    public void validate(Object obj, Errors errors) {
        Cliente cliente = (Cliente) obj;
        /**if (nadador.getNumTransaccion().equals("")) {
            errors.rejectValue("nom", "obligatori", "Cal introduir un valor");
        }*/
    }


}
