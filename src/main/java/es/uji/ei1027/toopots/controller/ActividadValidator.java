package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Actividad;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

public class ActividadValidator implements Validator {
    @Override
    public boolean supports(Class<?> cls) {
        return Actividad.class.equals(cls);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Actividad act = (Actividad) target;

        if (act.getEstadoActividad().equals("")) {
            errors.rejectValue("estado", "obligatorio", "Campo obligatorio.");
        }

        if (act.getNombre().equals("")) {
            errors.rejectValue("nombre", "obligatorio", "Campo obligatorio.");
        }

        if (act.getDescripción().equals("")) {
            errors.rejectValue("descripcion", "obligatorio", "Campo obligatorio.");
        }

        //Duración tiene que ser mayor a 0
        //if (act.getDuracion().equals("")) {
        //    errors.rejectValue("estado", "obligatorio", "Campo obligatorio.");
        //}

        if (act.getMinAsistentes() <= 0) {
            errors.rejectValue("asistentes", "obligatorio", "Tiene que haber más de 0 asistentes.");
        }

        if (act.getLugarActividad().equals("")) {
            errors.rejectValue("lugar", "obligatorio", "Campo obligatorio.");
        }

        if (act.getPuntoEncuentro().equals("")) {
            errors.rejectValue("encuentro", "obligatorio", "Campo obligatorio.");
        }

        if (act.getTextoCliente().equals("")) {
            errors.rejectValue("texto", "obligatorio", "Campo obligatorio.");
        }
    }
}
