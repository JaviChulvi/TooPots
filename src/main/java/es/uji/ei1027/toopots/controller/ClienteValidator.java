package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Cliente;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.ArrayList;
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

        int vectNumeros[] = {0,1,2,3,4,5,6,7,8,9};
        //char vectLetras[] = {'A','B','C','D','E','F','G','H','I','J','K','L','M','N','Ñ','O','P','Q','R','S','T','U','V','W','X','Y','Z'};
        ArrayList<Character> vec = new ArrayList<>();
        vec.add('0');
        vec.add('1');
        vec.add('2');
        vec.add('3');
        vec.add('4');
        vec.add('5');
        vec.add('6');
        vec.add('7');
        vec.add('8');
        vec.add('9');
        /**if (nadador.getNumTransaccion().equals("")) {
            errors.rejectValue("nom", "obligatori", "Cal introduir un valor");
        }*/
        if (cliente.getDni().equals("")) {
            errors.rejectValue("dni", "obligatori", "Debes rellenar el campo");
        }

        if (cliente.getDni().length() == 9 && Character.isLetter(cliente.getDni().charAt(8))== true) {

            for(int i=1 ; i<8 ; i++){
                if(!vec.contains(cliente.getDni().charAt(i))){
                    errors.rejectValue("dni", "obligatori", "El dni no coincide con el formato estandar");
                    break;
                }
            }

        }

        if (cliente.getDni().equals("admin")) {
            errors.rejectValue("dni", "obligatori", "Jaja buen intento");
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
