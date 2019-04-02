package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.AcreditaDao;
import es.uji.ei1027.toopots.model.Acredita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/acredita")
public class AcreditaController {

    private AcreditaDao acreditaDao;

    @Autowired
    public void setAcreditaDao(AcreditaDao acreditaDao) {
        this.acreditaDao = acreditaDao;
    }

    @RequestMapping("/list")
    public String listAcredita(Model model){
        model.addAttribute("acreditas", acreditaDao.getAcreditas());
        return "acredita/list";
    }

    @RequestMapping("/add")
    public String addAcredita(Model model) {
        model.addAttribute("acredita", new Acredita());
        return "acredita/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("acredita") Acredita acredita,
                                   BindingResult bindingResult) {
        AcreditaValidator acreditaValidator = new AcreditaValidator();
        acreditaValidator.validate(acredita, bindingResult);
        if (bindingResult.hasErrors())
            return "acredita/add";
        acreditaDao.addAcredita(acredita);
        return "redirect:list";
    }

    @RequestMapping(value="/update", method = RequestMethod.GET)
    public String editAcredita(Model model) {
        return "acredita/update";
    }

    @RequestMapping(value="/update", method = RequestMethod.POST)
    public String processUpdateSubmit(@ModelAttribute("entrada") Acredita acredita, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "acredita/update";
        acreditaDao.updateAcredita(acredita);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{tipoActividad}/{certificado}")
    public String processDelete(@PathVariable String tipoActividad, @PathVariable String certificado) {
        acreditaDao.deleteAcredita(tipoActividad, certificado);
        return "redirect:../list";
    }
}
