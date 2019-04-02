package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Instructor;
import es.uji.ei1027.toopots.model.tipos.EstadoInstructor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.Arrays;

public class InstructorValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) { return Instructor.class.equals(cls); }

    @Override
    public void validate(Object target, Errors errors) {
        Instructor instructor = (Instructor) target;

        if(instructor.getNombre().equals("")) {
            errors.rejectValue("nombre", "Valor necesario",
                    "Necesitas introducir un nombre");
        }
    }
}
