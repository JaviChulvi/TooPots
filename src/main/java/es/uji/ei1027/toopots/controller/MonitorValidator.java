package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class MonitorValidator implements Validator {

    @Override
    public boolean supports(Class<?> cls) { return Monitor.class.equals(cls); }

    @Override
    public void validate(Object target, Errors errors) {
        Monitor monitor = (Monitor) target;

        if(monitor.getNombre().equals("")) {
            errors.rejectValue("nombre", "Valor necesario",
                    "Necesitas introducir un nombre");
        }
    }
}
