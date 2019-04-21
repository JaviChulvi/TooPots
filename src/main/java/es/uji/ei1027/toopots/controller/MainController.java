package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ActividadDao;
import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Login;
import es.uji.ei1027.toopots.model.Monitor;
import org.jasypt.util.password.BasicPasswordEncryptor;
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
@RequestMapping("/")

public class MainController {

    private ClienteDao clienteDao;
    private MonitorDao monitorDao;
    private ActividadDao actividadDao;


    @Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao = monitorDao;
    }
    @Autowired
    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }
    @Autowired
    public void setActividadDao(ActividadDao actividadDao) {
        this.actividadDao = actividadDao;
    }

    @RequestMapping("/registro")
    public String registro(Model model) {
        return "registro";
    }

    @RequestMapping("/ajustes")
    public String ajustes(Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null) {
            return "redirect:login";
        } else {
            String tipo = (String) session.getAttribute("tipo");
            String dni = (String) session.getAttribute("dni");
            model.addAttribute("tipo", tipo);
            model.addAttribute("dni", dni);
            return "ajustes";
        }
    }

    @RequestMapping("/login")
    public String login(Model model) {
        model.addAttribute("login", new Login());
        return "login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("login") Login login,
                                   BindingResult bindingResult, Model model, HttpSession session) {
        LoginValidator loginValidator = new LoginValidator();
        loginValidator.validate(login, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        String tipo = tipoCuenta(login.getDni(), login.getPassword());
        if (tipo != null) {
            session.setAttribute("tipo", tipo);
            session.setAttribute("dni", login.getDni());
            return "redirect:" +tipo + "/list";
        }
        return "redirect:registro";
    }

    public String tipoCuenta(String dni, String password) {
        Monitor monitor = monitorDao.getMonitor(dni);
        Cliente cliente = clienteDao.getCliente(dni);
        BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
        if (monitor != null) {
            if (passwordEncryptor.checkPassword(password, monitor.getPassword())) {
                return "monitor";
            }
        } else if (cliente != null) {
            if (passwordEncryptor.checkPassword(password, cliente.getPassword())) {
                return "cliente";
            }
        }
        return null; // devuelve null si no existe la cuenta ni como cliente ni como monitor
    }

    @RequestMapping("/gestion")
    public String gestion(Model model,  HttpSession session) {
        if (session.getAttribute("tipo") == null && session.getAttribute("dni")==null || session.getAttribute("tipo") == "cliente") {
            return "redirect:login";
        } else {
            String dni = (String) session.getAttribute("dni");
            model.addAttribute("dni", dni);
            model.addAttribute("actividades", actividadDao.getActividadesMonitor(dni));
            return "gestion";
        }
    }

}
