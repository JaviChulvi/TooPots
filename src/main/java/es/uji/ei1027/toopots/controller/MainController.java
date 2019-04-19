package es.uji.ei1027.toopots.controller;

import es.uji.ei1027.toopots.dao.ClienteDao;
import es.uji.ei1027.toopots.dao.MonitorDao;
import es.uji.ei1027.toopots.model.Cliente;
import es.uji.ei1027.toopots.model.Login;
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

    private ClienteDao clienteDao;
    private MonitorDao monitorDao;

    @Autowired
    public void setMonitorDao(MonitorDao monitorDao) {
        this.monitorDao = monitorDao;
    }
    @Autowired
    public void setClienteDao(ClienteDao clienteDao) {
        this.clienteDao = clienteDao;
    }

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

        model.addAttribute("login", new Login());
        return "login";
    }

    @RequestMapping(value="/login", method= RequestMethod.POST)
    public String processAddSubmit(@ModelAttribute("login") Login login,
                                   BindingResult bindingResult, Model model) {
        LoginValidator loginValidator = new LoginValidator();
        loginValidator.validate(login, bindingResult);
        if (bindingResult.hasErrors()) {
            return "login";
        }
        String tipo = tipoCuenta(login.getDni(), login.getPassword());
        if (tipo != null) {
            return tipo + "/list";
        }
        return "registro";
    }

    public String tipoCuenta(String dni, String password) {
        Monitor monitor = monitorDao.getMonitor(dni);
        Cliente cliente = clienteDao.getCliente(dni);
        if (monitor != null) {
            if (monitor.getPassword().equals(password)) {
                return "monitor";
            }
        } else if (cliente != null) {
            if (cliente.getPassword().equals(password)) {
                return "cliente";
            }
        }
        return null; // devuelve null si no existe la cuenta ni como cliente ni como monitor
    }
}
