package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.EntradaDao;
import es.uji.ei1027.toopots.model.Entrada;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/entrada")
public class EntradaController {

    private EntradaDao entradaDao;

    @Autowired
    public void setEntradaDao(EntradaDao entradaDao) {
        this.entradaDao = entradaDao;
    }

    @RequestMapping("/list")
    public String listEntrada(Model model){
        model.addAttribute("entradas", entradaDao.getEntradas());
        return "entrada/list";
    }

    @RequestMapping("/add")
    public String addEntrada(Model model) {
        model.addAttribute("entrada", new Entrada());
        return "entrada/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("entrada") Entrada entrada,
                                   BindingResult bindingResult) {
        EntradaValidator entradaValidator = new EntradaValidator();
        entradaValidator.validate(entrada, bindingResult);
        if (bindingResult.hasErrors())
            return "entrada/add";
        entradaDao.addEntrada(entrada);
        return "redirect:list";
    }

    /*@RequestMapping(value="/update/{id}", method = RequestMethod.GET)
    public String editEntrada(Model model, @PathVariable int id) {
        model.addAttribute("entrada", entradaDao.getEntrada(id));
        return "entrada/update";
    }*/

    @RequestMapping(value="/update/{id}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable int id,
                                      @ModelAttribute("entrada") Entrada entrada,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "entrada/update";
        entradaDao.updateEntrada(entrada);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}")
    public String processDelete(@PathVariable int id) {
        entradaDao.deleteEntrada(id);
        return "redirect:../list";
    }
}
