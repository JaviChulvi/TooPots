package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.OfertaDao;
import es.uji.ei1027.toopots.model.Oferta;
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
@RequestMapping("/oferta")
public class OfertaController {

    private OfertaDao ofertaDao;

    @Autowired
    public void setOfertaDao(OfertaDao ofertaDao) {
        this.ofertaDao = ofertaDao;
    }

    @RequestMapping("/list")
    public String listOferta(Model model){
        model.addAttribute("ofertas", ofertaDao.getOfertas());
        return "oferta/list";
    }

    @RequestMapping("/add")
    public String addOferta(Model model, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            return "redirect:../../login";
        } else {
            model.addAttribute("oferta", new Oferta());
            return "oferta/add";
        }
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("oferta") Oferta oferta,
                                   BindingResult bindingResult) {
        OfertaValidator ofertaValidator = new OfertaValidator();
        ofertaValidator.validate(oferta, bindingResult);
        if (bindingResult.hasErrors())
            return "oferta/add";
        ofertaDao.addOferta(oferta);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{nombre}", method = RequestMethod.GET)
    public String editOferta(Model model, @PathVariable String nombre, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            return "redirect:../../login";
        } else {
            model.addAttribute("oferta", ofertaDao.getOferta(nombre));
            return "oferta/update";
        }
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("oferta") Oferta oferta,
                                      BindingResult bindingResult) {
        OfertaValidator ofertaValidator = new OfertaValidator();
        ofertaValidator.validate(oferta, bindingResult);
        if (bindingResult.hasErrors())
            return "oferta/update";
        ofertaDao.updateOferta(oferta);
        return "redirect:list";
    }

    @RequestMapping(value="/delete/{nombre}")
    public String processDelete(@PathVariable String nombre, HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni") == null || !session.getAttribute("dni").equals("admin")) {
            return "redirect:../../login";
        } else {
            ofertaDao.deleteOferta(nombre);
            return "redirect:../list";
        }
    }

}
