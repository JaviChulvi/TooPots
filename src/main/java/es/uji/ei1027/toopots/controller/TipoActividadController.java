package es.uji.ei1027.toopots.controller;


import es.uji.ei1027.toopots.dao.TipoActividadDao;
import es.uji.ei1027.toopots.model.TipoActividad;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/tipoactividad")
public class TipoActividadController {
    private TipoActividadDao tipoActividadDao;

    @Autowired
    public void setTipoActividadDao(TipoActividadDao tipoActividadDao) {
        this.tipoActividadDao = tipoActividadDao;
    }

    @RequestMapping(value="/add", method = RequestMethod.GET)
    public String addTipoActividad(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (!session.getAttribute("dni").equals("admin")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "tipoactividad/add");
                return "redirect:../login";
            }
        }
        model.addAttribute("tipoactividad", new TipoActividad());
        return "tipoactividad/add";
    }

    @RequestMapping(value="/add", method = RequestMethod.POST)
    public String procesarAddTipoActividad(@ModelAttribute("tipoactividad") TipoActividad tipoActividad) {
        tipoActividadDao.addTipoActividad(tipoActividad);
        return "redirect:../gestionTipoActividades";
    }

    @RequestMapping(value="/modificar/{id}", method = RequestMethod.GET)
    public String modificarTipoActividad(Model model, @PathVariable int id, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (!session.getAttribute("dni").equals("admin")) {
                    return "redirect:../../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "tipoactividad/modificar/"+id);
                return "redirect:../../login";
            }
        }
        model.addAttribute("tipoactividad", tipoActividadDao.getTipoActividad(id));
        return "tipoactividad/modificar";
    }

    @RequestMapping(value="/modificar/{id}", method = RequestMethod.POST)
    public String procesarModificarTipoActividad(@PathVariable int id,
                                      @ModelAttribute("tipoactividad") TipoActividad tipoActividad,
                                      BindingResult bindingResult) {
        tipoActividadDao.updateActividad(tipoActividad);
        return "redirect:../../gestionTipoActividades";
    }

    @RequestMapping(value="/eliminar/{id}")
    public String processDelete(@PathVariable int id, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            try {
                // session.getAttribute("dni") puede ser null
                if (!session.getAttribute("dni").equals("admin")) {
                    return "redirect:../../actividades";
                }
            } catch (Exception e) {
                session.setAttribute("urlAnterior", "tipoactividad/eliminar/"+id);
                return "redirect:../../login";
            }
        }
        tipoActividadDao.deleteTipoActividad(id);
        return "redirect:../../gestionTipoActividades";
    }
}