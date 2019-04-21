package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.model.Actividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/actividad")
public class ActividadController {

    private ActividadDao actividadDao;

    @Autowired
    public void setEntradaDao(ActividadDao actividadDao) {
        this.actividadDao = actividadDao;
    }

    @RequestMapping("/list")
    public String listActividades(Model model){
        model.addAttribute("actividades", actividadDao.getActividades());
        return "actividad/list";
    }

    @RequestMapping("/crear")
    public String addActividad(Model model) {
        model.addAttribute("actividad", new Actividad());
        return "actividad/crear";
    }

    @RequestMapping(value="/crear", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("actividad") Actividad actividad,
                                   BindingResult bindingResult) {
        ActividadValidator actividadValidator = new ActividadValidator();
        actividadValidator.validate(actividad, bindingResult);
        if (bindingResult.hasErrors())
            return "actividad/crear";
        actividadDao.addActividad(actividad);
        return "redirect:list";
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.GET)
    public String editActividad(Model model, @PathVariable int id) {
        model.addAttribute("actividad", actividadDao.getActividad(id));
        return "actividad/update";
    }

    @RequestMapping(value="/actualizar/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable int id,
                                      @ModelAttribute("actividad") Actividad actividad,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "actividad/actualizar";
        actividadDao.updateActividad(actividad);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id) {
        actividadDao.deleteActividad(id);
        return "redirect:../list";
    }
}
