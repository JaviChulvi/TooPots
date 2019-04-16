package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Imagen;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ImagenValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Imagen.class.equals(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Imagen img = (Imagen) target;

        if (img.getImagen() != "") {
            errors.rejectValue("imagen", "obligatorio", "Tiene que añadir una imagen");
        }
    }
}
