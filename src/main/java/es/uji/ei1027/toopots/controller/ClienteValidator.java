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
        if (cliente.getDni().equals("")) {
            errors.rejectValue("dni", "obligatori", "Debes rellenar el campo");
        }

        if (cliente.getNombre().equals("")) {
            errors.rejectValue("nombre", "obligatori", "Debes rellenar el campo");
        }

        if (!cliente.getCorreo().contains("@") || !cliente.getCorreo().contains(".") || cliente.getCorreo().equals("")) {
            errors.rejectValue("correo", "obligatori", "Introduce un email valido");
        }

        List<String> valors = Arrays.asList("hombre", "mujer");

        if(!valors.contains(cliente.getGenero())) {
            errors.rejectValue("genero", "valor incorrecte", "Debes de seleccionar: hombre o mujer");
        }
        if (cliente.getPassword().equals("")) {
            errors.rejectValue("password", "valor incorrecte", "Debes rellenar el campo");
        } else if (cliente.getPassword2().equals("")) {
            errors.rejectValue("password2", "valor incorrecte", "Debes confirmar la contraseña");
        } else if (!cliente.getPassword().equals(cliente.getPassword2())){
            errors.rejectValue("password2", "valor incorrecte", "La contraseña de verificación no coincide.");
        }
    }


}
