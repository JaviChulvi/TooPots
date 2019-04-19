package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Monitor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/")

public class MainController {

    @RequestMapping("/registro")
    public String registro(Model model) {
        model.addAttribute("cliente", new Cliente());
        model.addAttribute("monitor", new Monitor());
        return "registro";
    }

    @RequestMapping("/ajustes")
    public String ajustes(Model model) {
        return "ajustes";
    }

    @RequestMapping("/login")
    public String login(Model model) {
        return "login";
    }
}
