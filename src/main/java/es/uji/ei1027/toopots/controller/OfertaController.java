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

@Controller
@RequestMapping("/oferta")
public class OfertaController {


    private OfertaDao ofertaDao;

    @Autowired
    public void setReservaDao(OfertaDao ofertaDao) {
        this.ofertaDao = ofertaDao;
    }

    @RequestMapping("/list")
    public String listReserva(Model model){
        model.addAttribute("ofertas", ofertaDao.getOfertas());
        return "oferta/list";
    }

    @RequestMapping("/add")
    public String addReserva(Model model) {
        model.addAttribute("oferta", new Oferta());
        return "oferta/add";
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

    @RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editNadador(Model model, @PathVariable int id) {
        model.addAttribute("oferta", ofertaDao.getOferta(id));
        return "oferta/update";
    }

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable int id,
                                      @ModelAttribute("oferta") Oferta oferta,
                                      BindingResult bindingResult) {
        OfertaValidator ofertaValidator = new OfertaValidator();
        ofertaValidator.validate(oferta, bindingResult);
        if (bindingResult.hasErrors())
            return "oferta/update";
        ofertaDao.updateOferta(oferta);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id) {
        ofertaDao.deleteOferta(id);
        return "redirect:../list";
    }

}
