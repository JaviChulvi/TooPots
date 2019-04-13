package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.AcreditacionDao;
import es.uji.ei1027.toopots.model.Acreditacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/acreditacion")
public class AcreditacionController {

    private AcreditacionDao acreditacionDao;

    @Autowired
    public void setAcreditacionDao(AcreditacionDao acreditacionDao) {
        this.acreditacionDao = acreditacionDao;
    }

    @RequestMapping("/list")
    public String listAcreditacion(Model model){
        model.addAttribute("acreditaciones", acreditacionDao.getAcreditaciones());
        return "acreditacion/list";
    }

    @RequestMapping("/add")
    public String addAcreditacion(Model model) {
        model.addAttribute("acreditacion", new Acreditacion());
        return "acreditacion/add";
    }

    @RequestMapping(value="/add", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("acreditacion") Acreditacion acreditacion,
                                   BindingResult bindingResult) {
        AcreditacionValidator acreditacionValidator = new AcreditacionValidator();
        acreditacionValidator.validate(acreditacion, bindingResult);
        if (bindingResult.hasErrors())
            return "acreditacion/add";
        acreditacionDao.addAcreditacion(acreditacion);
        return "redirect:list";
    }

    @RequestMapping(value="/update/{id}/{certificado}", method = RequestMethod.GET)
    public String editAcreditacion(Model model, @PathVariable String dni, @PathVariable String certificado) {
        model.addAttribute("acreditacion", acreditacionDao.getAcreditacion(dni, certificado));
        return "acreditacion/update";
    }

    @RequestMapping(value="/update/{id}/{certificado}", method = RequestMethod.POST)
    public String processUpdateSubmit(@PathVariable int id, @PathVariable String certificado,
                                      @ModelAttribute("acreditacion") Acreditacion acreditacion,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "acreditacion/update";
        acreditacionDao.updateAcreditacion(acreditacion);
        return "redirect:../list";
    }

    @RequestMapping(value="/delete/{id}/{certificado}")
    public String processDelete(@PathVariable int id, @PathVariable String certificado) {
        acreditacionDao.deleteAcreditacion(certificado);
        return "redirect:../list";
    }
}
