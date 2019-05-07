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
    public String listOferta(Model model){
        model.addAttribute("descuentos", descuentoDao.getDescuentos());
        return "descuento/list";
    }

    @RequestMapping("/add")
    public String addOferta(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            return "redirect:../../login";
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
    public String editOferta(Model model, @PathVariable String nombre, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
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

    @RequestMapping(value="/delete/{nombre}")
    public String processDelete(@PathVariable String nombre, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            return "redirect:../../login";
        } else {
            descuentoDao.deleteDescuento(nombre);
            return "redirect:../list";
        }
    }

}
