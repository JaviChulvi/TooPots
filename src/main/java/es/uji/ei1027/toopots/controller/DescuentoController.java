package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.DescuentoDao;
import es.uji.ei1027.toopots.model.Descuento;
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
@RequestMapping("/descuento")
public class DescuentoController {

    private DescuentoDao descuentoDao;

    @Autowired
    public void setDescuentoDao(DescuentoDao descuentoDao) {
        this.descuentoDao = descuentoDao;
    }

    @RequestMapping("/list")
    public String listDescuento(Model model, HttpSession session){
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            try {
                if (!session.getAttribute("dni").equals("admin")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {
            }
            session.setAttribute("urlAnterior", "descuento/list");
            return "redirect:../login";
        } else {
            model.addAttribute("descuentos", descuentoDao.getDescuentos());
            return "descuento/list";
        }

    }

    @RequestMapping("/add")
    public String addDescuento(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            try {
                if (!session.getAttribute("dni").equals("admin")) {
                    return "redirect:../actividades";
                }
            } catch (Exception e) {

            }
            session.setAttribute("urlAnterior", "descuento/add");
            return "redirect:../login";
        } else {
            model.addAttribute("descuento", new Descuento());
            return "descuento/add";
        }
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("descuento") Descuento descuento,
                                   BindingResult bindingResult) {
        DescuentoValidator descuentoValidator = new DescuentoValidator();
        descuentoValidator.validate(descuento, bindingResult);
        if (bindingResult.hasErrors())
            return "descuento/add";
        descuentoDao.addDescuento(descuento);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{nombre}", method = RequestMethod.GET)
    public String editDescuento(Model model, @PathVariable String nombre, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            if (!session.getAttribute("dni").equals("admin")) {
                return "redirect:../actividades";
            }
            session.setAttribute("urlAnterior", "descuento/update/"+nombre);
            return "redirect:../../login";
        } else {
            model.addAttribute("descuento", descuentoDao.getDescuento(nombre));
            return "descuento/update";
        }
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("descuento")  Descuento descuento,
                                      BindingResult bindingResult) {
        DescuentoValidator descuentoValidator = new DescuentoValidator();
        descuentoValidator.validate(descuento, bindingResult);
        if (bindingResult.hasErrors())
            return "descuento/update";
        descuentoDao.updateDescuento(descuento);
        return "redirect:list";
    }

    @RequestMapping(value="/eliminar/{nombre}")
    public String getDelete(@PathVariable String nombre, HttpSession session, Model model) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            if (!session.getAttribute("dni").equals("admin")) {
                return "redirect:../actividades";
            }
            session.setAttribute("urlAnterior", "descuento/eliminar/"+nombre);
            return "redirect:../../login";
        } else {
            session.setAttribute("descuento", nombre);
            return "descuento/eliminar";
        }
    }

    @RequestMapping(value="/eliminar", method = RequestMethod.POST)
    public String processDelete( HttpSession session, Model model) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            if (!session.getAttribute("dni").equals("admin")) {
                return "redirect:../actividades";
            }
            session.setAttribute("urlAnterior", "descuento/list");
            return "redirect:../../login";
        } else {
            try {
                String nombre = (String) session.getAttribute("descuento");
                descuentoDao.deleteDescuento(nombre);
                session.removeAttribute("descuento");
            } catch (Exception e) {
                model.addAttribute("error", true);
                return "descuento/eliminar";
            }
            return "redirect:list";
        }
    }

}
